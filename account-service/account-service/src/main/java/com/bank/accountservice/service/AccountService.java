package com.bank.accountservice.service;

import com.bank.accountservice.dto.AccountDTO;
import com.bank.accountservice.dto.BalanceUpdateRequest;
import com.bank.accountservice.dto.CustomerDTO;
import com.bank.accountservice.entity.Account;
import com.bank.accountservice.exception.AccountFrozenException;
import com.bank.accountservice.exception.AccountNotFoundException;
import com.bank.accountservice.exception.CustomerValidationException;
import com.bank.accountservice.exception.InsufficientBalanceException;
import com.bank.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;

    @Value("${customer.service.url}")
    private String customerServiceUrl;


    public AccountDTO createAccount(AccountDTO accountDTO) {

        validateCustomerExists(accountDTO.getCustomerId());

        Account account = convertToEntity(accountDTO);
        account.setStatus("ACTIVE"); // default status on creation
        Account savedAccount = accountRepository.save(account);
        return convertToDTO(savedAccount);
    }


    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public AccountDTO getAccountById(Long accountId) {
        Account account = findAccountOrThrow(accountId);
        return convertToDTO(account);
    }


    public AccountDTO updateAccount(Long accountId, AccountDTO accountDTO) {
        Account existingAccount = findAccountOrThrow(accountId);


        if (!existingAccount.getCustomerId().equals(accountDTO.getCustomerId())) {
            validateCustomerExists(accountDTO.getCustomerId());
        }

        existingAccount.setCustomerId(accountDTO.getCustomerId());
        existingAccount.setAccountNumber(accountDTO.getAccountNumber());
        existingAccount.setAccountType(accountDTO.getAccountType());
        existingAccount.setBalance(accountDTO.getBalance());

        Account updatedAccount = accountRepository.save(existingAccount);
        return convertToDTO(updatedAccount);
    }


    public void deleteAccount(Long accountId) {
        Account account = findAccountOrThrow(accountId);
        accountRepository.delete(account);
    }


    public AccountDTO freezeAccount(Long accountId) {
        Account account = findAccountOrThrow(accountId);
        account.setStatus("FROZEN");
        Account updatedAccount = accountRepository.save(account);
        return convertToDTO(updatedAccount);
    }

    public AccountDTO activateAccount(Long accountId) {
        Account account = findAccountOrThrow(accountId);
        account.setStatus("ACTIVE");
        Account updatedAccount = accountRepository.save(account);
        return convertToDTO(updatedAccount);
    }


    public AccountDTO updateBalance(Long accountId, BalanceUpdateRequest request) {
        Account account = findAccountOrThrow(accountId);


        if ("FROZEN".equalsIgnoreCase(account.getStatus())) {
            throw new AccountFrozenException(
                    "Account " + accountId + " is FROZEN. Transaction not allowed.");
        }

        String type = request.getType().toUpperCase();
        Double amount = request.getAmount();

        if ("CREDIT".equals(type)) {

            account.setBalance(account.getBalance() + amount);

        } else if ("DEBIT".equals(type)) {

            if (account.getBalance() < amount) {
                throw new InsufficientBalanceException(
                        "Insufficient balance in account " + accountId
                                + ". Available: " + account.getBalance() + ", Requested: " + amount);
            }
            account.setBalance(account.getBalance() - amount);

        } else {
            throw new IllegalArgumentException("Invalid transaction type: " + type + ". Use CREDIT or DEBIT.");
        }

        Account updatedAccount = accountRepository.save(account);
        return convertToDTO(updatedAccount);
    }



    private Account findAccountOrThrow(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(
                        "Account not found with id: " + accountId));
    }

    // Calls Customer Service using RestTemplate to check customer exists
    private void validateCustomerExists(Long customerId) {
        try {
            String url = customerServiceUrl + "/" + customerId;
            CustomerDTO customer = restTemplate.getForObject(url, CustomerDTO.class);

            if (customer == null) {
                throw new CustomerValidationException(
                        "Customer not found with id: " + customerId);
            }
        } catch (RestClientException ex) {
            throw new CustomerValidationException(
                    "Customer not found with id: " + customerId + " (Customer Service unreachable or customer does not exist)");
        }
    }

    private AccountDTO convertToDTO(Account account) {
        return new AccountDTO(
                account.getAccountId(),
                account.getCustomerId(),
                account.getAccountNumber(),
                account.getAccountType(),
                account.getBalance(),
                account.getStatus()
        );
    }

    private Account convertToEntity(AccountDTO accountDTO) {
        return new Account(
                accountDTO.getAccountId(),
                accountDTO.getCustomerId(),
                accountDTO.getAccountNumber(),
                accountDTO.getAccountType(),
                accountDTO.getBalance(),
                accountDTO.getStatus()
        );
    }
}
