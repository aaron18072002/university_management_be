package com.coding.university_management.University.Management.controller;

import com.coding.university_management.University.Management.dto.request.UserCreationRequest;
import com.coding.university_management.University.Management.dto.request.UserUpdateRequest;
import com.coding.university_management.University.Management.dto.response.ApiResponse;
import com.coding.university_management.University.Management.entity.User;
import com.coding.university_management.University.Management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Tạo tài khoản thành công",
                this.userService.createUser(request)
        );

        return apiResponse;
    }

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable(name = "userId") String userId) {
        return this.userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser
            (@PathVariable(name = "userId") String userId,
             @RequestBody UserUpdateRequest request) {
        return this.userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        this.userService.deleteUser(userId);

        return "User has been deleted";
    }

}
