package com.bankingBackend.bankingApi.DTO.role_permission;

public class CreateRoleDTO {
    private String role;

    public CreateRoleDTO() {
    }

    public CreateRoleDTO(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
