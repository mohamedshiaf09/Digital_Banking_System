package com.bank.loanservice.service;

import com.bank.loanservice.dto.LoanDTO;
import com.bank.loanservice.entity.Loan;
import com.bank.loanservice.exception.CustomerValidationException;
import com.bank.loanservice.exception.InvalidLoanStatusException;
import com.bank.loanservice.exception.LoanNotFoundException;
import com.bank.loanservice.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.bank.loanservice.dto.AccountDTO;
import com.bank.loanservice.dto.DepositWithdrawRequest;
import com.bank.loanservice.dto.TransactionDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final RestTemplate restTemplate;

    @Value("${account.service.url}")
    private String accountServiceUrl;

    @Value("${transaction.service.url}")
    private String transactionServiceUrl;

    // Allowed loan types - business rule
    private static final List<String> ALLOWED_LOAN_TYPES =
            List.of("PERSONAL", "HOME", "VEHICLE", "EDUCATION", "BUSINESS");

    // CREATE - POST /loan
    public LoanDTO createLoan(LoanDTO loanDTO) {
        // Business rule 1: validate customer exists (RestTemplate call to Customer Service)
        validateAccountExists(loanDTO.getAccountId());

        // Business rule 2: loan type must be one of the allowed types
        String loanType = loanDTO.getLoanType().toUpperCase();
        if (!ALLOWED_LOAN_TYPES.contains(loanType)) {
            throw new IllegalArgumentException(
                    "Invalid loan type: " + loanDTO.getLoanType()
                            + ". Allowed types: " + ALLOWED_LOAN_TYPES);
        }

        Loan loan = convertToEntity(loanDTO);
        loan.setLoanType(loanType);
        loan.setStatus("PENDING"); // Business rule 3: every new loan starts as PENDING

        Loan savedLoan = loanRepository.save(loan);

// Notify Transaction Service
        DepositWithdrawRequest request =
                new DepositWithdrawRequest(
                        savedLoan.getAccountId(),
                        savedLoan.getLoanAmount()
                );

        restTemplate.postForObject(
                transactionServiceUrl + "/deposit",
                request,
                TransactionDTO.class
        );

        return convertToDTO(savedLoan);
    }

    // READ ALL - GET /loan
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // READ ONE - GET /loan/{id}
    public LoanDTO getLoanById(Long loanId) {
        Loan loan = findLoanOrThrow(loanId);
        return convertToDTO(loan);
    }

    // APPROVE - PUT /loan/approve/{id}
    public LoanDTO approveLoan(Long loanId) {
        Loan loan = findLoanOrThrow(loanId);

        // Business rule: can only approve a loan that is still PENDING
        if (!"PENDING".equalsIgnoreCase(loan.getStatus())) {
            throw new InvalidLoanStatusException(
                    "Loan " + loanId + " cannot be approved. Current status: " + loan.getStatus()
                            + " (only PENDING loans can be approved)");
        }

        loan.setStatus("APPROVED");
        Loan updatedLoan = loanRepository.save(loan);
        return convertToDTO(updatedLoan);
    }

    // REJECT - PUT /loan/reject/{id}
    public LoanDTO rejectLoan(Long loanId) {
        Loan loan = findLoanOrThrow(loanId);

        // Business rule: can only reject a loan that is still PENDING
        if (!"PENDING".equalsIgnoreCase(loan.getStatus())) {
            throw new InvalidLoanStatusException(
                    "Loan " + loanId + " cannot be rejected. Current status: " + loan.getStatus()
                            + " (only PENDING loans can be rejected)");
        }

        loan.setStatus("REJECTED");
        Loan updatedLoan = loanRepository.save(loan);
        return convertToDTO(updatedLoan);
    }

    // DELETE - DELETE /loan/{id}
    public void deleteLoan(Long loanId) {
        Loan loan = findLoanOrThrow(loanId);
        loanRepository.delete(loan);
    }

    // ---------- Helper methods ----------

    private Loan findLoanOrThrow(Long loanId) {
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(
                        "Loan not found with id: " + loanId));
    }

    // Calls Customer Service using RestTemplate to check customer exists
    private void validateAccountExists(Long accountId) {
        try {
            String url = accountServiceUrl + "/" + accountId;

            AccountDTO account = restTemplate.getForObject(url, AccountDTO.class);

            if (account == null) {
                throw new CustomerValidationException(
                        "Account not found with id: " + accountId);
            }
        } catch (RestClientException ex) {
            throw new CustomerValidationException(
                    "Account not found with id: " + accountId
                            + " (Account Service unreachable or customer does not exist)");
        }
    }

    private LoanDTO convertToDTO(Loan loan) {
        return new LoanDTO(
                loan.getLoanId(),
                loan.getAccountId(),
                loan.getLoanAmount(),
                loan.getLoanType(),
                loan.getStatus()
        );
    }

    private Loan convertToEntity(LoanDTO loanDTO) {
        return new Loan(
                loanDTO.getLoanId(),
                loanDTO.getAccountId(),
                loanDTO.getLoanAmount(),
                loanDTO.getLoanType(),
                loanDTO.getStatus()
        );
    }
}
