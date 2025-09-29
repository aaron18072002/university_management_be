package com.coding.university_management.University.Management.controller;

import com.coding.university_management.University.Management.dto.request.TinChiCreateRequest;
import com.coding.university_management.University.Management.dto.response.ApiResponse;
import com.coding.university_management.University.Management.dto.response.TinChiResponse;
import com.coding.university_management.University.Management.service.TinChiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tin-chi")
public class TinChiController {

    private final TinChiService tinChiService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TinChiResponse> create
            (@RequestBody @Valid TinChiCreateRequest request) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), "Tạo tín chỉ thành công",
                tinChiService.create(request));
    }

    @GetMapping
    public ApiResponse<java.util.List<TinChiResponse>> getAll() {
        return new ApiResponse<>(HttpStatus.OK.value(), "Lấy danh sách tín chỉ thành công",
                tinChiService.getAll());
    }

}
