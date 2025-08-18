package com.coding.university_management.University.Management.configuration;

import com.coding.university_management.University.Management.entity.Permission;
import com.coding.university_management.University.Management.entity.Role;
import com.coding.university_management.University.Management.entity.User;
import com.coding.university_management.University.Management.repository.PermissionRepository;
import com.coding.university_management.University.Management.repository.RoleRepository;
import com.coding.university_management.University.Management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfigInit {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "12345678";

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                // 1. Define all permissions
                Map<String, String> permissions = Map.ofEntries(
                        Map.entry("student:read", "Xem thông tin sinh viên"),
                        Map.entry("student:create", "Tạo sinh viên"),
                        Map.entry("student:update", "Cập nhật sinh viên"),
                        Map.entry("student:delete", "Xóa sinh viên"),
                        Map.entry("course:read", "Xem thông tin môn học"),
                        Map.entry("course:create", "Tạo môn học"),
                        Map.entry("course:update", "Cập nhật môn học"),
                        Map.entry("course:delete", "Xóa môn học"),
                        Map.entry("schedule:read", "Xem lịch học"),
                        Map.entry("schedule:create", "Tạo lịch học"),
                        Map.entry("schedule:update", "Cập nhật lịch học"),
                        Map.entry("schedule:delete", "Xóa lịch học"),
                        Map.entry("grade:read", "Xem điểm"),
                        Map.entry("grade:update", "Cập nhật điểm"),
                        Map.entry("tuition:read", "Xem học phí"),
                        Map.entry("tuition:create", "Tạo học phí"),
                        Map.entry("tuition:update", "Cập nhật học phí"),
                        Map.entry("credit:register", "Quyền đăng ký tín chỉ"),
                        Map.entry("credit:cancel", "Quyền hủy tín chỉ")
                );

                permissions.forEach((name, description) -> {
                    if (permissionRepository.findById(name).isEmpty()) {
                        permissionRepository.save(Permission.builder().name(name).description(description).build());
                    }
                });

                // 2. Define roles and assign permissions
                // SINHVIEN Role
                Set<Permission> studentPermissions = Set.of("student:read", "course:read", "schedule:read", "grade:read", "tuition:read", "credit:register", "credit:cancel")
                        .stream().map(permissionRepository::findById).map(p -> p.orElse(null)).collect(Collectors.toSet());
                roleRepository.save(Role.builder().name("SINHVIEN").description("Vai trò sinh viên").permissions(studentPermissions).build());

                // GIAOVIEN Role
                Set<Permission> teacherPermissions = Set.of("student:read", "course:read", "schedule:read", "grade:read", "grade:update")
                        .stream().map(permissionRepository::findById).map(p -> p.orElse(null)).collect(Collectors.toSet());
                roleRepository.save(Role.builder().name("GIAOVIEN").description("Vai trò giáo viên").permissions(teacherPermissions).build());

                // KETOAN Role
                Set<Permission> accountantPermissions = Set.of("student:read", "tuition:read", "tuition:create", "tuition:update")
                        .stream().map(permissionRepository::findById).map(p -> p.orElse(null)).collect(Collectors.toSet());
                roleRepository.save(Role.builder().name("KETOAN").description("Vai trò kế toán").permissions(accountantPermissions).build());

                // QUANTRIVIEN Role (Admin) - has all permissions
                Set<Permission> adminPermissions = new HashSet<>(permissionRepository.findAll());
                Role adminRole = roleRepository.save(Role.builder().name("QUANTRIVIEN").description("Vai trò quản trị viên").permissions(adminPermissions).build());

                // 3. Create admin user
                Set<Role> roles = new HashSet<>();
                roles.add(adminRole);

                User user = User.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: {}, please change it", ADMIN_PASSWORD);
            }
            log.info("Application initialization completed .....");
        };
    }
}