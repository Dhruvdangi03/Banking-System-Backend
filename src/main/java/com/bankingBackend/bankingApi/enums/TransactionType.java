package com.bankingBackend.bankingApi.enums;

public enum TransactionType {
    TRANSFER("TRANSFER"),
    DEPOSIT("DEPOSIT"),
    WITHDRAWAL("WITHDRAWAL");

    private final String strValue;

    TransactionType(String strValue) {
        this.strValue = strValue;
    }

    public String getValue() {
        return strValue;
    }
}
