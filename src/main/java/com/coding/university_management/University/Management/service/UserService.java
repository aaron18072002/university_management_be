package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.UserCreationRequest;
import com.coding.university_management.University.Management.dto.request.UserUpdateRequest;
import com.coding.university_management.University.Management.dto.response.UserResponse;
import com.coding.university_management.University.Management.entity.Role;
import com.coding.university_management.University.Management.entity.User;
import com.coding.university_management.University.Management.entity.UserRole;
import com.coding.university_management.University.Management.entity.id.UserRoleId;
import com.coding.university_management.University.Management.exception.AppException;
import com.coding.university_management.University.Management.exception.ErrorCode;
import com.coding.university_management.University.Management.exception.NotFoundException;
import com.coding.university_management.University.Management.mapper.UserMapper;
import com.coding.university_management.University.Management.repository.RoleRepository;
import com.coding.university_management.University.Management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if(this.userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = this.userMapper.toUser(request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        Set<String> roles = new HashSet<>();
//        roles.add(Role.USER.name());
//        user.setRoles(roles);

        return this.userMapper.toUserResponse(this.userRepository.save(user));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> getUsers() {
        return this.userRepository.findAll().stream()
                .map(user -> this.userMapper.toUserResponse(user))
                .toList();
    }

    // authentication.name trong Spring Security chính là username (subject)
    // được lấy từ JWT sau khi nó được giải mã (decoded).
    // Nếu != thì Spring Security tự động ném ra lỗi 403 Forbidden (AccessDeniedException)
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUser(String id) {
        return this.userMapper.toUserResponse(this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng")));
    }

    public UserResponse getMyInfo() {
        SecurityContext context = SecurityContextHolder.getContext();

        // Spring lấy getName() từ claim "sub" của token.
        String username = context.getAuthentication().getName();

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return this.userMapper.toUserResponse(user);
    }

    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_EXISTED.getMessage()));
        // Mapping biến request vào biến user
        this.userMapper.updateUser(user, request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        List<Role> roles = this.roleRepository.findAllById(request.getRoles());
        // Tạo các đối tượng UserRole với khóa chính được khởi tạo đầy đủ
        List<UserRole> userRoles = this.assignRolesToUser(user, roles);

        // SỬA LỖI: Chỉnh sửa collection hiện có thay vì thay thế nó
        // 1. Xóa tất cả các role cũ. Do có orphanRemoval=true, các bản ghi trong USERS_ROLES sẽ bị xóa.
        user.getRoles().clear();
        // 2. Thêm tất cả các role mới. Các bản ghi mới sẽ được tạo trong USERS_ROLES.
        user.getRoles().addAll(userRoles);

        return this.userMapper.toUserResponse(this.userRepository.save(user));

    }

    public void deleteUser(String userId) {
        this.userRepository.deleteById(userId);
    }

    /**
     * Hàm private helper để tạo và gán các đối tượng UserRole cho một User.
     * Hàm này chịu trách nhiệm khởi tạo khóa chính phức hợp UserRoleId.
     *
     * @param user  Đối tượng User cần được gán role.
     * @param roles Danh sách các đối tượng Role sẽ được gán.
     * @return Một Set các đối tượng UserRole đã sẵn sàng để được lưu.
     */
    private List<UserRole> assignRolesToUser(User user, List<Role> roles) {
        return roles.stream()
                .map(role -> {
                    // Khởi tạo khóa chính phức hợp một cách tường minh
                    UserRoleId userRoleId = new UserRoleId(user.getId(), role.getName());
                    UserRole userRole = new UserRole();
                    userRole.setId(userRoleId);
                    userRole.setUser(user);
                    userRole.setRole(role);

                    return userRole;
                })
                .toList(); // Thu thập trực tiếp vào Set cho hiệu quả
    }

}
