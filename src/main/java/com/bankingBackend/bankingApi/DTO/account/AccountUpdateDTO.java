package com.bankingBackend.bankingApi.DTO.account;

public class AccountUpdateDTO {
    private String accountType;
    private String userEmail;

    public AccountUpdateDTO() {
    }

    public AccountUpdateDTO(String accountType, String userEmail) {
        this.accountType = accountType;
        this.userEmail = userEmail;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
