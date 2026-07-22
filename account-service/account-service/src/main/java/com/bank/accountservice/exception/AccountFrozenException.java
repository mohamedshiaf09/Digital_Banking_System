package com.bank.accountservice.exception;

public class AccountFrozenException extends RuntimeException {

    public AccountFrozenException(String message) {
        super(message);
    }
}
