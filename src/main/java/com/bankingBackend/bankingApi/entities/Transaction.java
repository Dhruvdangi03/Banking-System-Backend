package com.bankingBackend.bankingApi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @NotBlank(message = "Amount can't be Null")
    private Long amount;

    @NotBlank
    private String timestamp;

    @NotBlank
    private String description;

    @Column(name = "account_Number",  nullable = false)
    private Long accountNumber;

    @Column(name = "related_account_Number")
    private Long relatedAccountNumber;

    public Transaction() {
    }

    public Transaction(Long relatedAccountNumber, Long accountNumber, String description, String timestamp, Long amount, String transactionType, Long id) {
        this.relatedAccountNumber = relatedAccountNumber;
        this.accountNumber = accountNumber;
        this.description = description;
        this.timestamp = timestamp;
        this.amount = amount;
        this.transactionType = transactionType;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getRelatedAccountNumber() {
        return relatedAccountNumber;
    }

    public void setRelatedAccountNumber(Long relatedAccountNumber) {
        this.relatedAccountNumber = relatedAccountNumber;
    }
}
