package com.coding.university_management.University.Management.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DangKyTinChiRequest {
    String maSinhVien;
    String maTinChi;
    String maKiHoc;
    String trangThai;
    LocalDate ngayDangKy;
}