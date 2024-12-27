package com.bankingBackend.bankingApi.DTO.account;

public class AccountDTO {
    private Long balance;
    private String accountType;

    public AccountDTO() {
    }

    public AccountDTO(Long balance, String accountType) {
        this.balance = balance;
        this.accountType = accountType;
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
}
