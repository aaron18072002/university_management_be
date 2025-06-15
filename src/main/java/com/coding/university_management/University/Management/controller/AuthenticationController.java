package com.coding.university_management.University.Management.controller;

import com.coding.university_management.University.Management.dto.request.AuthenticationRequest;
import com.coding.university_management.University.Management.dto.response.ApiResponse;
import com.coding.university_management.University.Management.dto.response.AuthenticationResponse;
import com.coding.university_management.University.Management.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate
            (@RequestBody AuthenticationRequest request)
    {
        boolean isAuthenticated = authenticationService.authenticate(request);

        HttpStatus status = isAuthenticated ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        String message = isAuthenticated ? "Đăng nhập thành công" : "Bạn đã nhập sai mật khẩu";

        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .authenticated(isAuthenticated)
                .build();

        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(
                status.value(), message, authResponse
        );

        return ResponseEntity.status(status).body(response);
    }

}
