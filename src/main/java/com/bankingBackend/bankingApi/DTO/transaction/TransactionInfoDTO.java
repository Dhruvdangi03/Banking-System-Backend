package com.bankingBackend.bankingApi.DTO.transaction;

public class TransactionInfoDTO {
    private String transactionType;
    private Long amount;
    private String timestamp;
    private String description;
    private Long accountNumber;
    private Long relatedAccountNumber;

    public TransactionInfoDTO() {
    }

    public TransactionInfoDTO(String transactionType, Long amount, String timestamp, String description, Long accountNumber, Long relatedAccountNumber) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
        this.accountNumber = accountNumber;
        this.relatedAccountNumber = relatedAccountNumber;
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
