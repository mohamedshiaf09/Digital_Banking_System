package com.bank.transactionservice.repository;

import com.bank.transactionservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Fetch all transactions where this account was either the sender or the receiver
    List<Transaction> findByFromAccountOrToAccountOrderByTransactionDateDesc(
            Long fromAccount, Long toAccount);
}
