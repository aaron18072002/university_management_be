package com.coding.university_management.University.Management.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class SinhVienResponse {
    // SinhVien fields
    private String maSinhVien;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private LocalDate ngayNhapHoc;
    private LocalDate ngayTotNghiep;

    // ChiTietSinhVien fields
    private String diaChi;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String quocTich;
    private String cccd;
    private String sdtNguoiThan;

    // NganhHoc
    private NganhHocResponse nganhHoc;

    // User - returning UserResponse instead of just ID and username
    private UserResponse user;
}
