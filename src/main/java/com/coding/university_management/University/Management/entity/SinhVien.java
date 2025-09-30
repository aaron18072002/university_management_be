package com.coding.university_management.University.Management.entity;

import com.coding.university_management.University.Management.enums.GioiTinh;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "SINHVIENS")
public class SinhVien {

    @Id
    @Column(name = "ma_sinh_vien", columnDefinition = "CHAR(36)")
    String maSinhVien;

    @Column(name = "ho_ten", columnDefinition = "VARCHAR(50)")
    String hoTen;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", unique = true)
    String email;

    @Column(name = "so_dien_thoai", columnDefinition = "CHAR(10)", unique = true)
    String soDienThoai;

    @Column(name = "ngay_nhap_hoc", columnDefinition = "DATE")
    LocalDate ngayNhapHoc;

    @Column(name = "ngay_tot_nghiep", columnDefinition = "DATE")
    LocalDate ngayTotNghiep;

    // Fields from ChiTietSinhVien
    @Column(name = "dia_chi", columnDefinition = "VARCHAR(100)")
    String diaChi;

    @Column(name = "ngay_sinh", columnDefinition = "DATE")
    LocalDate ngaySinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "gioi_tinh", columnDefinition = "VARCHAR(10)")
    GioiTinh gioiTinh;

    @Column(name = "quoc_tich", columnDefinition = "CHAR(50) DEFAULT 'Viet Nam'")
    String quocTich;

    @Column(name = "cccd", columnDefinition = "CHAR(12)", unique = true)
    String cccd;

    @Column(name = "sdt_nguoi_than", columnDefinition = "CHAR(10)")
    String sdtNguoiThan;

    @ManyToOne
    @JoinColumn(name = "ma_nganh_hoc", referencedColumnName = "ma_nganh_hoc")
    NganhHoc nganhHoc;

    @OneToMany(mappedBy = "sinhVien", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<KetQuaHocTap> ketQuaHocTaps = new HashSet<>();

    @ManyToMany(mappedBy = "sinhViens")
    Set<LichHoc> lichHocs = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}