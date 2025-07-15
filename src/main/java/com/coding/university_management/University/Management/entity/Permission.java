package com.coding.university_management.University.Management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "PERMISSIONS")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "name", columnDefinition = "CHAR(36)")
    String name;

    @Column(name = "description", columnDefinition = "VARCHAR(30)")
    String description;

    // Mối quan hệ một-nhiều với bảng trung gian RolePermission.
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RolePermission> roles = new HashSet<>();

}
