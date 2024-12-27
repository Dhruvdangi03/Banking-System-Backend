package com.bankingBackend.bankingApi.repos;

import com.bankingBackend.bankingApi.DTO.role_permission.RolePermissionDTO;
import com.bankingBackend.bankingApi.DTO.role_permission.SimpleRolePermissionDTO;
import com.bankingBackend.bankingApi.entities.RolePermissions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionsRepository extends JpaRepository<RolePermissions, Long> {
    @Query(value = "SELECT * FROM role_permissions r WHERE r.api = :api AND r.role_id = :roleId AND r.permission_id = :permissionId", nativeQuery = true)
    RolePermissions findRolePermissions(String api, long roleId, long permissionId);

    @Query("SELECT new com.bankingBackend.bankingApi.DTO.role_permission.SimpleRolePermissionDTO(" +
            "    r.role," +
            "    p.permission) " +
            "FROM " +
            "    RolePermissions rp " +
            "JOIN " +
            "    Role r ON rp.roleId = r.id " +
            "JOIN " +
            "    Permission p ON rp.permissionId = p.id " +
            "WHERE " +
            "    rp.api = :api")
    List<SimpleRolePermissionDTO> findPermissions(String api);

    @Query("SELECT new com.bankingBackend.bankingApi.DTO.role_permission.RolePermissionDTO(" +
            "    rp.api, " +
            "    r.role, " +
            "    p.permission) " +
            "FROM " +
            "    RolePermissions rp " +
            "JOIN " +
            "    Role r ON rp.roleId = r.id " +
            "JOIN " +
            "    Permission p ON rp.permissionId = p.id")
    List<RolePermissionDTO> findAllRolePermissions();
}
