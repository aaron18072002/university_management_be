package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.SinhVienCreateRequest;
import com.coding.university_management.University.Management.dto.response.SinhVienResponse;
import com.coding.university_management.University.Management.entity.ChiTietSinhVien;
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
                .build();
    }

    public ChiTietSinhVien toChiTietEntity(SinhVienCreateRequest request) {
        if (request == null) return null;

        return ChiTietSinhVien.builder()
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

        ChiTietSinhVien chiTiet = sinhVien.getChiTietSinhVien();

        return SinhVienResponse.builder()
                .maSinhVien(sinhVien.getMaSinhVien())
                .hoTen(sinhVien.getHoTen())
                .email(sinhVien.getEmail())
                .soDienThoai(sinhVien.getSoDienThoai())
                .ngayNhapHoc(sinhVien.getNgayNhapHoc())
                .ngayTotNghiep(sinhVien.getNgayTotNghiep())
                .nganhHoc(nganhHocMapper.toNganhHocResponse(sinhVien.getNganhHoc()))
                .diaChi(chiTiet != null ? chiTiet.getDiaChi() : null)
                .ngaySinh(chiTiet != null ? chiTiet.getNgaySinh() : null)
                .gioiTinh(chiTiet != null && chiTiet.getGioiTinh() != null ? chiTiet.getGioiTinh().name() : null)
                .quocTich(chiTiet != null ? chiTiet.getQuocTich() : null)
                .cccd(chiTiet != null ? chiTiet.getCccd() : null)
                .sdtNguoiThan(chiTiet != null ? chiTiet.getSdtNguoiThan() : null)
                .user(sinhVien.getUser() != null ? userMapper.toUserResponse(sinhVien.getUser()) : null)
                .build();
    }
}
