package com.bankingBackend.bankingApi.DTO.role_permission;

import java.util.List;

public class PermissionDTO {
    private String api;
    private long roleId;
    private List<Long> permissionId;

    public PermissionDTO() {
    }

    public PermissionDTO(String api, long roleId, List<Long> permissionId) {
        this.api = api;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(List<Long> permissionId) {
        this.permissionId = permissionId;
    }
}
