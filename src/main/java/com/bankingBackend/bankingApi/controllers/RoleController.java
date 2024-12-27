package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.role_permission.CreateRoleDTO;
import com.bankingBackend.bankingApi.entities.Role;
import com.bankingBackend.bankingApi.services.RolePermissionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RolePermissionsService rolePermissionsService;

    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody CreateRoleDTO role){
        return ResponseEntity.ok(rolePermissionsService.createRole(role));
    }

    @GetMapping("/all-roles")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = rolePermissionsService.getAllRoles();
        if (roles == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(roles);
    }
}
