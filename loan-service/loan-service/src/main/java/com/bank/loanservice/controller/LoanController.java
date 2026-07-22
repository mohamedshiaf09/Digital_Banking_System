package com.bank.loanservice.controller;

import com.bank.loanservice.dto.LoanDTO;
import com.bank.loanservice.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    // CREATE - POST /loan
    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@Valid @RequestBody LoanDTO loanDTO) {
        LoanDTO createdLoan = loanService.createLoan(loanDTO);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    // READ ALL - GET /loan
    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanDTO> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    // READ ONE - GET /loan/{id}  (bonus, beyond SRS's minimum, but useful)
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> getLoanById(@PathVariable("id") Long loanId) {
        LoanDTO loan = loanService.getLoanById(loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    // APPROVE - PUT /loan/approve/{id}
    @PutMapping("/approve/{id}")
    public ResponseEntity<LoanDTO> approveLoan(@PathVariable("id") Long loanId) {
        LoanDTO approvedLoan = loanService.approveLoan(loanId);
        return new ResponseEntity<>(approvedLoan, HttpStatus.OK);
    }

    // REJECT - PUT /loan/reject/{id}
    @PutMapping("/reject/{id}")
    public ResponseEntity<LoanDTO> rejectLoan(@PathVariable("id") Long loanId) {
        LoanDTO rejectedLoan = loanService.rejectLoan(loanId);
        return new ResponseEntity<>(rejectedLoan, HttpStatus.OK);
    }

    // DELETE - DELETE /loan/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable("id") Long loanId) {
        loanService.deleteLoan(loanId);
        return new ResponseEntity<>("Loan deleted successfully", HttpStatus.OK);
    }
}
