package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.RoleRequest;
import com.coding.university_management.University.Management.dto.response.RoleResponse;
import com.coding.university_management.University.Management.entity.Permission;
import com.coding.university_management.University.Management.entity.Role;
import com.coding.university_management.University.Management.entity.RolePermission;
import com.coding.university_management.University.Management.entity.id.RolePermissionId;
import com.coding.university_management.University.Management.mapper.RoleMapper;
import com.coding.university_management.University.Management.repository.PermissionRepository;
import com.coding.university_management.University.Management.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Transactional
    public RoleResponse create(RoleRequest request) {
        Role role = this.roleMapper.toRole(request);

        List<Permission> permissions = this.permissionRepository.findAllById(request.getPermissions());

        Role finalRole = role;
        List<RolePermission> rolePermissions = permissions.stream()
                .map(permission -> {
                    RolePermissionId id = new RolePermissionId(finalRole.getName(), permission.getName());
                    RolePermission rolePermission = new RolePermission();
                    rolePermission.setId(id);
                    rolePermission.setRole(finalRole);
                    rolePermission.setPermission(permission);
                    return rolePermission;
                }).toList();

        role.setPermissions(new HashSet<>(rolePermissions));

        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return this.roleRepository.findAll()
                .stream()
                .map(role -> this.roleMapper.toRoleResponse(role))
                .toList();
    }

    @Transactional
    public void delete(String roleId) {
        this.roleRepository.deleteById(roleId);
    }

}
