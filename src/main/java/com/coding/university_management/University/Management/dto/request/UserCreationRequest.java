package com.coding.university_management.University.Management.dto.request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserCreationRequest {

    @Size(min = 2, max = 20, message = "Tên tài khoản chỉ được chứa từ 2 đến 20 ký tự")
    private String username;

    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;

    public UserCreationRequest() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

}
