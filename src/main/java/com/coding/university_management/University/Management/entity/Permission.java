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
@Table(name = "PERMISSIONS")
public class Permission {

    @Id
    @Column(name = "name", columnDefinition = "CHAR(36)")
    String name;

    @Column(name = "description", columnDefinition = "VARCHAR(30)")
    String description;

    // Mối quan hệ một-nhiều với bảng trung gian RolePermission.
    // Loại trừ trường này khỏi toString() để phá vỡ vòng lặp đệ quy
    @ToString.Exclude
    @OneToMany(mappedBy = "permission", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RolePermission> roles = new HashSet<>();

}
