package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.UserCreationRequest;
import com.coding.university_management.University.Management.dto.request.UserUpdateRequest;
import com.coding.university_management.University.Management.dto.response.UserResponse;
import com.coding.university_management.University.Management.entity.User;
import com.coding.university_management.University.Management.enums.Role;
import com.coding.university_management.University.Management.exception.AppException;
import com.coding.university_management.University.Management.exception.ErrorCode;
import com.coding.university_management.University.Management.exception.NotFoundException;
import com.coding.university_management.University.Management.mapper.UserMapper;
import com.coding.university_management.University.Management.repository.UserRepository;
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
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {
        if(this.userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = this.userMapper.toUSer(request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));

        Set<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

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

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
        this.userMapper.updateUser(user, request);

        return this.userMapper.toUserResponse(this.userRepository.save(user));

    }

    public void deleteUser(String userId) {
        this.userRepository.deleteById(userId);
    }

}
