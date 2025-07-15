package com.coding.university_management.University.Management.entity.id;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class RolePermissionId implements Serializable {

    @Column(name = "role_name", nullable = false, columnDefinition = "CHAR(36)")
    private String roleId;

    @Column(name = "permission_name", nullable = false, columnDefinition = "CHAR(36)")
    private String permissionId;

    public RolePermissionId() {}

    public RolePermissionId(String roleId, String permissionId) {
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermissionId that = (RolePermissionId) o;
        return Objects.equals(roleId, that.roleId) &&
                Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, permissionId);
    }

}
