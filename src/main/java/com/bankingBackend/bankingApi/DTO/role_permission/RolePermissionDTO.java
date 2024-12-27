package com.bankingBackend.bankingApi.DTO.role_permission;

public class RolePermissionDTO {
    private String api;
    private String role;
    private String permission;

    public RolePermissionDTO() {
    }

    public RolePermissionDTO(String api, String role, String permission) {
        this.api = api;
        this.role = role;
        this.permission = permission;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
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
