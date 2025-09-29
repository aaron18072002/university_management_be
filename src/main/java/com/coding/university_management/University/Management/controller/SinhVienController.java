package com.coding.university_management.University.Management.controller;

import com.coding.university_management.University.Management.dto.request.SinhVienCreateRequest;
import com.coding.university_management.University.Management.dto.response.ApiResponse;
import com.coding.university_management.University.Management.dto.response.SinhVienResponse;
import com.coding.university_management.University.Management.service.SinhVienService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sinh-vien")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SinhVienController {

    SinhVienService sinhVienService;

    @PostMapping
    public ApiResponse<SinhVienResponse> create(@RequestBody SinhVienCreateRequest request) {
        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Tạo sinh viên thành công",
                sinhVienService.create(request)
        );
    }

    @GetMapping
    public ApiResponse<List<SinhVienResponse>> getAll() {
        return new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lấy danh sách sinh viên thành công",
                sinhVienService.getAll()
        );
    }

}
