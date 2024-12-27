package com.bankingBackend.bankingApi.DTO.transaction;

public class TransferDTO {
    private long amount;
    private long accountNumber;
    private String description;

    public TransferDTO() {
    }

    public TransferDTO(long amount, long accountNumber, String description) {
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.description = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
