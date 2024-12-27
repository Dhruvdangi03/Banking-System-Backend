package com.bankingBackend.bankingApi.services;
import com.bankingBackend.bankingApi.DTO.role_permission.*;
import com.bankingBackend.bankingApi.entities.Permission;
import com.bankingBackend.bankingApi.entities.Role;
import com.bankingBackend.bankingApi.entities.RolePermissions;
import com.bankingBackend.bankingApi.exceptions.ResourceNotFoundException;
import com.bankingBackend.bankingApi.repos.PermissionRepository;
import com.bankingBackend.bankingApi.repos.RolePermissionsRepository;
import com.bankingBackend.bankingApi.repos.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionsService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Role createRole(CreateRoleDTO role) {
        Role newRole = new Role();
        newRole.setRole(role.getRole());

        return roleRepository.save(newRole);
    }

    public Permission createPermission(CreatePermissionDTO permission) {
        Permission newPermission = new Permission();
        newPermission.setPermission(permission.getPermissions());

        return permissionRepository.save(newPermission);
    }

    public void createRolePermission(PermissionDTO permissionDTO) {
        for (int i = 0; i < permissionDTO.getPermissionId().size(); i++) {
            RolePermissions rolePermissions = new RolePermissions();
            rolePermissions.setApi(permissionDTO.getApi());
            rolePermissions.setRoleId(permissionDTO.getRoleId());
            rolePermissions.setPermissionId(permissionDTO.getPermissionId().get(i));

            rolePermissionsRepository.save(rolePermissions);
        }
    }

    public void deleteRolePermission(PermissionDTO permissionDTO) {
        for (int i = 0; i < permissionDTO.getPermissionId().size(); i++){
            RolePermissions rolePermission = rolePermissionsRepository.findRolePermissions(permissionDTO.getApi(), permissionDTO.getRoleId(), permissionDTO.getPermissionId().get(i));
            if(rolePermission == null) throw new ResourceNotFoundException("Role-Permission not found with RoleId " + permissionDTO.getRoleId() + " on PermissionId " + permissionDTO.getPermissionId().get(i));
            rolePermissionsRepository.delete(rolePermission);
        }
    }

    public List<SimpleRolePermissionDTO> getPermissions(String api) {
        return rolePermissionsRepository.findPermissions(api);
    }

    public boolean hasPermission(String api, String role, String type){
        List<SimpleRolePermissionDTO> rolePermissionList = getPermissions(api);

        for(SimpleRolePermissionDTO dto: rolePermissionList){
            if(role.equals(dto.getRole()) && type.equals(dto.getPermission())){
                return true;
            }
        }

        return false;
    }

    public List<RolePermissionDTO> getAllRolePermissions() {
        return rolePermissionsRepository.findAllRolePermissions();
    }
}
