package com.bank.accountservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long accountId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[0-9]{10,16}$", message = "Account number must be 10 to 16 digits")
    private String accountNumber;

    @NotBlank(message = "Account type is required")
    private String accountType;

    @Positive(message = "Balance must be positive")
    private Double balance;

    private String status;
}
