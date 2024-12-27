package com.bankingBackend.bankingApi.DTO.role_permission;

public class CreatePermissionDTO {
    private String permissions;

    public CreatePermissionDTO() {
    }

    public CreatePermissionDTO(String permissions) {
        this.permissions = permissions;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}
