package com.bank.loanservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long accountId;
    private Long customerId;
    private String accountNumber;
    private String accountType;
    private Double balance;
    private String status;
}