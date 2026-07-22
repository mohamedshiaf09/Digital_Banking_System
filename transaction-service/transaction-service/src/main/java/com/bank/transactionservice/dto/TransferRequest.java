package com.bank.transactionservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {

    @NotNull(message = "From account is required")
    private Long fromAccount;

    @NotNull(message = "To account is required")
    private Long toAccount;

    @Positive(message = "Amount must be positive")
    private Double amount;
}
