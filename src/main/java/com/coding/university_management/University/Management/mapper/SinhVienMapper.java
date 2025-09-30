package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.SinhVienCreateRequest;
import com.coding.university_management.University.Management.dto.response.SinhVienResponse;
import com.coding.university_management.University.Management.entity.SinhVien;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SinhVienMapper {

    private final NganhHocMapper nganhHocMapper;
    private final UserMapper userMapper;

    public SinhVien toEntity(SinhVienCreateRequest request) {
        if (request == null) return null;

        return SinhVien.builder()
                .hoTen(request.getHoTen())
                .email(request.getEmail())
                .soDienThoai(request.getSoDienThoai())
                .ngayNhapHoc(request.getNgayNhapHoc())
                .ngayTotNghiep(request.getNgayTotNghiep())
                .diaChi(request.getDiaChi())
                .ngaySinh(request.getNgaySinh())
                .gioiTinh(request.getGioiTinh())
                .quocTich(request.getQuocTich())
                .cccd(request.getCccd())
                .sdtNguoiThan(request.getSdtNguoiThan())
                .build();
    }

    public SinhVienResponse toResponse(SinhVien sinhVien) {
        if (sinhVien == null) return null;

        return SinhVienResponse.builder()
                .maSinhVien(sinhVien.getMaSinhVien())
                .hoTen(sinhVien.getHoTen())
                .email(sinhVien.getEmail())
                .soDienThoai(sinhVien.getSoDienThoai())
                .ngayNhapHoc(sinhVien.getNgayNhapHoc())
                .ngayTotNghiep(sinhVien.getNgayTotNghiep())
                .nganhHoc(nganhHocMapper.toNganhHocResponse(sinhVien.getNganhHoc()))
                .diaChi(sinhVien.getDiaChi())
                .ngaySinh(sinhVien.getNgaySinh())
                .gioiTinh(sinhVien.getGioiTinh() != null ? sinhVien.getGioiTinh().name() : null)
                .quocTich(sinhVien.getQuocTich())
                .cccd(sinhVien.getCccd())
                .sdtNguoiThan(sinhVien.getSdtNguoiThan())
                .user(sinhVien.getUser() != null ? userMapper.toUserResponse(sinhVien.getUser()) : null)
                .build();
    }
}
