package com.bank.transactionservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    // null for a pure DEPOSIT (money entering from outside the bank)
    private Long fromAccount;

    // null for a pure WITHDRAW (money leaving the bank)
    private Long toAccount;

    private Double amount;

    // DEPOSIT, WITHDRAW, or TRANSFER
    private String transactionType;

    private LocalDateTime transactionDate;
}
