package com.bank.loanservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    private Long loanId;

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @Positive(message = "Loan amount must be positive")
    private Double loanAmount;

    @NotBlank(message = "Loan type is required")
    private String loanType;

    private String status;
}
