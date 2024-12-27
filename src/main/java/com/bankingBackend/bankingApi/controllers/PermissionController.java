package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.role_permission.CreatePermissionDTO;
import com.bankingBackend.bankingApi.entities.Permission;
import com.bankingBackend.bankingApi.services.RolePermissionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private RolePermissionsService rolePermissionsService;

    @PostMapping
    public ResponseEntity<?> createPermission(@Valid @RequestBody CreatePermissionDTO permission){
        return ResponseEntity.ok(rolePermissionsService.createPermission(permission));
    }

    @GetMapping("/all-permissions")
    public ResponseEntity<?> getAllPermissions() {
        List<Permission> permissions = rolePermissionsService.getAllPermissions();
        if (permissions == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(permissions);
    }
}
