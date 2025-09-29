package com.coding.university_management.University.Management.dto.request;

import com.coding.university_management.University.Management.enums.GioiTinh;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SinhVienCreateRequest {
    // SinhVien fields
    private String hoTen;
    private String email;
    private String soDienThoai;
    private LocalDate ngayNhapHoc;
    private String maNganhHoc;

    // ChiTietSinhVien fields
    private String diaChi;
    private LocalDate ngaySinh;
    private GioiTinh gioiTinh;
    private String quocTich;
    private String cccd;
    private String sdtNguoiThan;

    // User fields
    private String username;
    private String password;
}
