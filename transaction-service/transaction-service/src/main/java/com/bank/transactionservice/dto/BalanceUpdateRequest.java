package com.bank.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Mirrors Account Service's BalanceUpdateRequest.
 * Used as the request body when calling
 * PUT http://localhost:8082/accounts/{id}/balance
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceUpdateRequest {

    private Double amount;
    private String type; // "CREDIT" or "DEBIT"
}
