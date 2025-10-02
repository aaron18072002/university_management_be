package com.coding.university_management.University.Management.controller;

import com.coding.university_management.University.Management.dto.request.DangKyTinChiRequest;
import com.coding.university_management.University.Management.dto.response.ApiResponse;
import com.coding.university_management.University.Management.dto.response.DangKyTinChiResponse;
import com.coding.university_management.University.Management.service.DangKyTinChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dang-ky-tin-chi")
public class DangKyTinChiController {

    private final DangKyTinChiService dangKyTinChiService;

    @Autowired
    public DangKyTinChiController(DangKyTinChiService dangKyTinChiService) {
        this.dangKyTinChiService = dangKyTinChiService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DangKyTinChiResponse>>> getAll() {
        List<DangKyTinChiResponse> responses = dangKyTinChiService.getAll();
        ApiResponse<List<DangKyTinChiResponse>> apiResponse =
                new ApiResponse<>(200, "Lấy danh sách đăng ký tín chỉ thành công", responses);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/ki-hoc/{maKiHoc}")
    public ResponseEntity<ApiResponse<List<DangKyTinChiResponse>>> getByHocKi(@PathVariable String maKiHoc) {
        List<DangKyTinChiResponse> responses = dangKyTinChiService.getByKiHoc(maKiHoc);
        ApiResponse<List<DangKyTinChiResponse>> apiResponse =
                new ApiResponse<>(200, "Lấy danh sách đăng ký tín chỉ theo kỳ học thành công", responses);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DangKyTinChiResponse>> create(@RequestBody DangKyTinChiRequest request) {
        DangKyTinChiResponse response = dangKyTinChiService.create(request);
        ApiResponse<DangKyTinChiResponse> apiResponse =
                new ApiResponse<>(201, "Đăng ký tín chỉ thành công", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/sinh-vien/{maSinhVien}")
    public ResponseEntity<ApiResponse<List<DangKyTinChiResponse>>> getByMaSinhVien(@PathVariable String maSinhVien) {
        List<DangKyTinChiResponse> responses = dangKyTinChiService.getBySinhVien(maSinhVien);
        ApiResponse<List<DangKyTinChiResponse>> apiResponse =
                new ApiResponse<>(200, "Lấy danh sách đăng ký tín chỉ của sinh viên thành công", responses);
        return ResponseEntity.ok(apiResponse);
    }
}
