package com.bankingBackend.bankingApi.DTO.user;

public class EmailRoleDTO {
    private String email;
    private String role;

    public EmailRoleDTO() {
    }

    public EmailRoleDTO(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
