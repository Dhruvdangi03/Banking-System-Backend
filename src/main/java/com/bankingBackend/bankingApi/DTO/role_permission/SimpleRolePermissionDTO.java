package com.bankingBackend.bankingApi.DTO.role_permission;

public class SimpleRolePermissionDTO {
    private String role;
    private String permission;

    public SimpleRolePermissionDTO() {
    }

    public SimpleRolePermissionDTO(String role, String permission) {
        this.role = role;
        this.permission = permission;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
