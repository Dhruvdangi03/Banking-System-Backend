package com.bankingBackend.bankingApi.repos;

import com.bankingBackend.bankingApi.DTO.transaction.TransactionInfoDTO;
import com.bankingBackend.bankingApi.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT * FROM transaction t WHERE t.transaction_id = :transactionId", nativeQuery = true)
    Transaction getTransactionById(@Param("transactionId") Long transactionId);

    @Query("SELECT com.bankingBackend.bankingApi.DTO.transaction.TransactionInfoDTO" +
            "(t.transactionType, t.amount, t.timestamp, t.description, t.accountNumber, t.relatedAccountNumber)" +
            " FROM transaction t WHERE t.accountNumber = :accountNumber")
    List<TransactionInfoDTO> getTransactionsByAccountNumber(@Param("accountNumber") Long accountNumber);
}
