package com.bankingBackend.bankingApi.entities;

import jakarta.persistence.*;

@Entity
@Table
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String permission;

    public Permission() {
    }

    public Permission(long id, String permission) {
        this.id = id;
        this.permission = permission;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
