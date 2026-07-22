package com.bank.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceUpdateRequest {

    @Positive(message = "Amount must be positive")
    private Double amount;

    // Expected values: "CREDIT" or "DEBIT"
    @NotBlank(message = "Transaction type is required")
    private String type;
}
