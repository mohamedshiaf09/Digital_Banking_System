package com.bank.loanservice.exception;

public class InvalidLoanStatusException extends RuntimeException {

    public InvalidLoanStatusException(String message) {
        super(message);
    }
}
