package com.coding.university_management.University.Management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DangKyTinChiResponse {
    String id;
    SinhVienResponse sinhVien;
    TinChiResponse tinChi;
    KiHocResponse kiHoc;
    String trangThai;
    LocalDate ngayDangKy;
}