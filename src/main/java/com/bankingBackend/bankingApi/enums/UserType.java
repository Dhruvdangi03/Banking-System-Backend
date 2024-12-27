package com.bankingBackend.bankingApi.enums;

public enum UserType {
    CUSTOMER("CUSTOMER"),
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE");

    private final String strValue;

    UserType(String strValue) {
        this.strValue = strValue;
    }

    public String getValue() {
        return strValue;
    }
}
