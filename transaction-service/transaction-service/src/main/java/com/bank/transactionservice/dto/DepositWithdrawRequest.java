package com.bank.transactionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositWithdrawRequest {

    @NotNull(message = "Account ID is required")
    private Long accountId;

    @Positive(message = "Amount must be positive")
    private Double amount;
}
