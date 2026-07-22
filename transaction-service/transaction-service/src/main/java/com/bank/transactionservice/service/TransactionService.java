package com.bank.transactionservice.service;

import com.bank.transactionservice.dto.BalanceUpdateRequest;
import com.bank.transactionservice.dto.DepositWithdrawRequest;
import com.bank.transactionservice.dto.TransactionDTO;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.entity.Transaction;
import com.bank.transactionservice.exception.TransactionFailedException;
import com.bank.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    @Value("${account.service.url}")
    private String accountServiceUrl;

    // ---------- DEPOSIT ----------
    public TransactionDTO deposit(DepositWithdrawRequest request) {
        // Business rule: money enters the bank -> credit the account
        creditAccount(request.getAccountId(), request.getAmount());

        Transaction transaction = new Transaction(
                null,
                null,                       // no "from" - money came from outside the bank
                request.getAccountId(),
                request.getAmount(),
                "DEPOSIT",
                LocalDateTime.now()
        );

        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    // ---------- WITHDRAW ----------
    public TransactionDTO withdraw(DepositWithdrawRequest request) {
        // Business rule: money leaves the bank -> debit the account
        // Account Service itself checks sufficient balance + frozen status
        debitAccount(request.getAccountId(), request.getAmount());

        Transaction transaction = new Transaction(
                null,
                request.getAccountId(),
                null,                       // no "to" - money left the bank
                request.getAmount(),
                "WITHDRAW",
                LocalDateTime.now()
        );

        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    // ---------- TRANSFER ----------
    public TransactionDTO transfer(TransferRequest request) {

        // Business rule: cannot transfer to the same account
        if (request.getFromAccount().equals(request.getToAccount())) {
            throw new TransactionFailedException("Cannot transfer money to the same account.");
        }

        // Step 1: Debit sender's account (Account Service checks sufficient balance & frozen status)
        debitAccount(request.getFromAccount(), request.getAmount());

        // Step 2: Credit receiver's account
        try {
            creditAccount(request.getToAccount(), request.getAmount());
        } catch (RuntimeException ex) {
            // Business rule: if crediting the receiver fails (e.g. invalid account),
            // roll back by crediting the sender's money back - keeps balances consistent
            creditAccount(request.getFromAccount(), request.getAmount());
            throw new TransactionFailedException(
                    "Transfer failed while crediting receiver account. Sender's amount has been refunded. Reason: "
                            + ex.getMessage());
        }

        // Step 3: Record the transaction only after both steps succeed
        Transaction transaction = new Transaction(
                null,
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount(),
                "TRANSFER",
                LocalDateTime.now()
        );

        Transaction saved = transactionRepository.save(transaction);
        return convertToDTO(saved);
    }

    // ---------- HISTORY ----------
    public List<TransactionDTO> getHistory(Long accountId) {
        List<Transaction> transactions = transactionRepository
                .findByFromAccountOrToAccountOrderByTransactionDateDesc(accountId, accountId);

        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------- Helper methods: calling Account Service ----------

    private void creditAccount(Long accountId, Double amount) {
        updateAccountBalance(accountId, amount, "CREDIT");
    }

    private void debitAccount(Long accountId, Double amount) {
        updateAccountBalance(accountId, amount, "DEBIT");
    }

    private void updateAccountBalance(Long accountId, Double amount, String type) {
        String url = accountServiceUrl + "/" + accountId + "/balance";
        BalanceUpdateRequest requestBody = new BalanceUpdateRequest(amount, type);

        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    new HttpEntity<>(requestBody),
                    Object.class
            );
        } catch (HttpClientErrorException ex) {
            // Account Service returned a 4xx error (insufficient balance, frozen account, not found)
            // Extract its actual error message so the user gets a clear reason
            throw new TransactionFailedException(
                    "Account Service rejected the request for account " + accountId + ": "
                            + ex.getResponseBodyAsString());
        } catch (RestClientException ex) {
            throw new TransactionFailedException(
                    "Could not reach Account Service for account " + accountId + ". " + ex.getMessage());
        }
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getTransactionId(),
                transaction.getFromAccount(),
                transaction.getToAccount(),
                transaction.getAmount(),
                transaction.getTransactionType(),
                transaction.getTransactionDate()
        );
    }
}
