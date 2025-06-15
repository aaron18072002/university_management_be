package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.UserCreationRequest;
import com.coding.university_management.University.Management.dto.request.UserUpdateRequest;
import com.coding.university_management.University.Management.dto.response.UserResponse;
import com.coding.university_management.University.Management.entity.User;
import com.coding.university_management.University.Management.exception.AppException;
import com.coding.university_management.University.Management.exception.ErrorCode;
import com.coding.university_management.University.Management.exception.NotFoundException;
import com.coding.university_management.University.Management.mapper.UserMapper;
import com.coding.university_management.University.Management.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    public User createUser(UserCreationRequest request) {
        if(this.userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = this.userMapper.toUSer(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return this.userRepository.save(user);
    }

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public UserResponse getUser(String id) {
        return this.userMapper.toUserResponse(this.userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng")));
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
