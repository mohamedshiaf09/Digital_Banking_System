package com.bank.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long transactionId;
    private Long fromAccount;
    private Long toAccount;
    private Double amount;
    private String transactionType;
    private LocalDateTime transactionDate;
}
