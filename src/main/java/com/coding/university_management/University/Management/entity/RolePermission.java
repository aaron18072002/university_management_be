package com.coding.university_management.University.Management.entity;

import com.coding.university_management.University.Management.entity.id.RolePermissionId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter // Dùng @Getter và @Setter thay cho @Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROLES_PERMISSIONS")
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @ToString.Exclude // Thêm để tránh lỗi StackOverflow khi logging
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId") // <-- Phải khớp với "roleId" trong RolePermissionId
    @JoinColumn(name = "role_name", referencedColumnName = "name")
    private Role role;

    @ToString.Exclude // Thêm để tránh lỗi StackOverflow khi logging
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId") // <-- Phải khớp với "permissionId" trong RolePermissionId
    @JoinColumn(name = "permission_name", referencedColumnName = "name")
    private Permission permission;

}
