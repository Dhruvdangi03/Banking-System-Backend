package com.bankingBackend.bankingApi.controllers;

import com.bankingBackend.bankingApi.DTO.role_permission.GetPermissionDTO;
import com.bankingBackend.bankingApi.DTO.role_permission.PermissionDTO;
import com.bankingBackend.bankingApi.DTO.role_permission.RolePermissionDTO;
import com.bankingBackend.bankingApi.DTO.role_permission.SimpleRolePermissionDTO;
import com.bankingBackend.bankingApi.services.OtherService;
import com.bankingBackend.bankingApi.services.RolePermissionsService;
import com.bankingBackend.bankingApi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private RolePermissionsService rolePermissionsService;
    @Autowired
    private OtherService service;

    @GetMapping("/get_permission")
    public ResponseEntity<?> getPermissions(@Valid @RequestBody GetPermissionDTO permissionDTO) {
        List<SimpleRolePermissionDTO> simpleRolePermissionDTOS = rolePermissionsService.getPermissions(permissionDTO.getApi());
        if (simpleRolePermissionDTOS == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(simpleRolePermissionDTOS);
    }

    @GetMapping("/getAll_permissions")
    public ResponseEntity<?> getAllPermissions() {
        List<RolePermissionDTO> rolePermissionDTOS = rolePermissionsService.getAllRolePermissions();
        if (rolePermissionDTOS == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rolePermissionDTOS);
    }

    @PostMapping("/create_permission")
    public ResponseEntity<?> createPermission(@Valid @RequestBody PermissionDTO permissionDTO){
        rolePermissionsService.createRolePermission(permissionDTO);
        return ResponseEntity.ok(permissionDTO);
    }

    @DeleteMapping("/delete_permission")
    public ResponseEntity<?> deletePermission(@Valid @RequestBody PermissionDTO permissionDTO) {
        rolePermissionsService.deleteRolePermission(permissionDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/make_admin")
    public ResponseEntity<?> makeAdmin(@Valid @RequestBody String email){
        userService.makeAdmin(email);

        return ResponseEntity.ok(email + " is an Admin now !");
    }

    @PutMapping("/make_employee")
    public ResponseEntity<?> makeEmployee(@Valid @RequestBody String email){
        userService.makeEmployee(email);

        return ResponseEntity.ok(email + " is an Employee now !");
    }

    @GetMapping("/get_logs")
    public ResponseEntity<?> getLogs() {
        return ResponseEntity.ok(service.getLogs());
    }
}
