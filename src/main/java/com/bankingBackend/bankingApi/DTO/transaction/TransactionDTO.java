package com.bankingBackend.bankingApi.DTO.transaction;

public class TransactionDTO {
    private Long amount;
    private String description;

    public TransactionDTO() {
    }

    public TransactionDTO(Long amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
