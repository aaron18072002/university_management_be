package com.coding.university_management.University.Management.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "DANGKY_TINCHI")
public class DangKyTinChi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private String id;

    @ManyToOne
    @JoinColumn(name = "ma_sinh_vien", referencedColumnName = "ma_sinh_vien")
    private SinhVien sinhVien;

    @ManyToOne
    @JoinColumn(name = "ma_tin_chi", referencedColumnName = "ma_tin_chi")
    private TinChi tinChi;

    @ManyToOne
    @JoinColumn(name = "ma_ki_hoc", referencedColumnName = "ma_ki_hoc")
    private KiHoc kiHoc;

    @Column(name = "trang_thai", columnDefinition = "VARCHAR(20)")
    private String trangThai; // DANG_KY, HOAN_THANH, HUY

    @Column(name = "ngay_dang_ky", columnDefinition = "DATE")
    private LocalDate ngayDangKy;
}
