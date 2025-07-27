package com.coding.university_management.University.Management.entity.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionId implements Serializable {

    // Tên trường này phải khớp với giá trị trong @MapsId("roleId") của RolePermission.java
    private String roleId;

    // Tên trường này phải khớp với giá trị trong @MapsId("permissionId") của RolePermission.java
    private String permissionId;

}
