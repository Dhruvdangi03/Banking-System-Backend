package com.bankingBackend.bankingApi.entities;

import jakarta.persistence.*;

@Entity
@Table
public class RolePermissions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String api;
    @Column(nullable = false)
    private long roleId;
    @Column(nullable = false)
    private long permissionId;

    public RolePermissions() {
    }

    public RolePermissions(long id, String api, long roleId, long permissionId) {
        this.id = id;
        this.api = api;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }
}
