package com.bank.accountservice.controller;

import com.bank.accountservice.dto.AccountDTO;
import com.bank.accountservice.dto.BalanceUpdateRequest;
import com.bank.accountservice.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // CREATE - POST /accounts
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // READ ALL - GET /accounts
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // READ ONE - GET /accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable("id") Long accountId) {
        AccountDTO account = accountService.getAccountById(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // UPDATE - PUT /accounts/{id}
    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(
            @PathVariable("id") Long accountId,
            @Valid @RequestBody AccountDTO accountDTO) {
        AccountDTO updatedAccount = accountService.updateAccount(accountId, accountDTO);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    // DELETE - DELETE /accounts/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable("id") Long accountId) {
        accountService.deleteAccount(accountId);
        return new ResponseEntity<>("Account deleted successfully", HttpStatus.OK);
    }

    // BALANCE UPDATE - PUT /accounts/{id}/balance
    // Called internally by Transaction Service for deposit/withdraw/transfer
    @PutMapping("/{id}/balance")
    public ResponseEntity<AccountDTO> updateBalance(
            @PathVariable("id") Long accountId,
            @Valid @RequestBody BalanceUpdateRequest request) {
        AccountDTO updatedAccount = accountService.updateBalance(accountId, request);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }

    // FREEZE - PUT /accounts/freeze/{id}
    @PutMapping("/freeze/{id}")
    public ResponseEntity<AccountDTO> freezeAccount(@PathVariable("id") Long accountId) {
        AccountDTO frozenAccount = accountService.freezeAccount(accountId);
        return new ResponseEntity<>(frozenAccount, HttpStatus.OK);
    }

    // ACTIVATE - PUT /accounts/activate/{id}
    @PutMapping("/activate/{id}")
    public ResponseEntity<AccountDTO> activateAccount(@PathVariable("id") Long accountId) {
        AccountDTO activatedAccount = accountService.activateAccount(accountId);
        return new ResponseEntity<>(activatedAccount, HttpStatus.OK);
    }
}
