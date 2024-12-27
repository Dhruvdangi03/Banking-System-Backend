package com.bankingBackend.bankingApi.DTO.account;

public class AccountInfo {
    private Long balance;
    private String accountType;
    private Long accountNumber;
    private String userEmail;
    private String timeOfCreation;

    public AccountInfo() {
    }

    public AccountInfo(Long balance, String accountType, Long accountNumber, String userEmail, String timeOfCreation) {
        this.balance = balance;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.userEmail = userEmail;
        this.timeOfCreation = timeOfCreation;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
