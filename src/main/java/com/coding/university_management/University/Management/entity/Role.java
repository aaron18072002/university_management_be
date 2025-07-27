package com.coding.university_management.University.Management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter // Dùng @Getter và @Setter thay cho @Data
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @Column(name = "name", columnDefinition = "CHAR(36)")
    String name;

    @Column(name = "description", columnDefinition = "VARCHAR(30)")
    String description;

    // Mối quan hệ một-nhiều với bảng trung gian RolePermission.
    // 'mappedBy = "role"' chỉ ra rằng mối quan hệ này được quản lý bởi trường 'role' trong Entity RolePermission.
    // CascadeType.ALL: Khi một Role được lưu/xóa, các RolePermission liên quan cũng sẽ được lưu/xóa.
    // orphanRemoval = true: Khi một RolePermission bị xóa khỏi tập hợp này, nó cũng sẽ bị xóa khỏi database.
    // Loại trừ trường này khỏi toString() để phá vỡ vòng lặp đệ quy
    @ToString.Exclude
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RolePermission> permissions = new HashSet<>();

    // Mối quan hệ một-nhiều tới bảng trung gian UserRole
    @ToString.Exclude
    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<UserRole> users = new HashSet<>();

}
