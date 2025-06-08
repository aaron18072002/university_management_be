package com.coding.university_management.University.Management.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @Size(min = 2, max = 20, message = "Tên tài khoản chỉ được chứa từ 2 đến 20 ký tự")
    private String username;

    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;

}
