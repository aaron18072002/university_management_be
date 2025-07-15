package com.coding.university_management.University.Management.entity;

import com.coding.university_management.University.Management.entity.id.RolePermissionId;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROLES_PERMISSIONS")
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    // Mối quan hệ nhiều-một tới Role.
    // @MapsId chỉ định rằng trường 'roleId' trong @EmbeddedId được lấy từ khóa chính của Role.
    // @JoinColumn chỉ định cột khóa ngoại trong bảng ROLES_PERMISSIONS.
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_name", referencedColumnName = "name")
    private Role role;

    // Mối quan hệ nhiều-một tới Permission.
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permissionId")
    @JoinColumn(name = "permission_name", referencedColumnName = "name")
    private Permission permission;

}
