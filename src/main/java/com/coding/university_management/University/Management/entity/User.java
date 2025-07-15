package com.coding.university_management.University.Management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "CHAR(36)")
    String id;

    @Column(name = "username", columnDefinition = "VARCHAR(30)")
    String username;

    @Column(name = "password", columnDefinition = "VARCHAR(255)")
    String password;

    @Column(name = "first_name", columnDefinition = "VARCHAR(30)")
    String firstName;

    @Column(name = "last_name", columnDefinition = "VARCHAR(30)")
    String lastName;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    LocalDate dob;

    // Mối quan hệ một-nhiều tới bảng trung gian UserRole
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<UserRole> roles = new HashSet<>();

}
