package com.bank.transactionservice.controller;

import com.bank.transactionservice.dto.DepositWithdrawRequest;
import com.bank.transactionservice.dto.TransactionDTO;
import com.bank.transactionservice.dto.TransferRequest;
import com.bank.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // POST /deposit
    @PostMapping("/deposit")
    public ResponseEntity<TransactionDTO> deposit(@Valid @RequestBody DepositWithdrawRequest request) {
        TransactionDTO result = transactionService.deposit(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // POST /withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdraw(@Valid @RequestBody DepositWithdrawRequest request) {
        TransactionDTO result = transactionService.withdraw(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // POST /transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionDTO> transfer(@Valid @RequestBody TransferRequest request) {
        TransactionDTO result = transactionService.transfer(request);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // GET /history/{accountId}
    @GetMapping("/history/{accountId}")
    public ResponseEntity<List<TransactionDTO>> getHistory(@PathVariable("accountId") Long accountId) {
        List<TransactionDTO> history = transactionService.getHistory(accountId);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }
}
