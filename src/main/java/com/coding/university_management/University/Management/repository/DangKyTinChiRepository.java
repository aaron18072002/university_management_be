package com.coding.university_management.University.Management.repository;

import com.coding.university_management.University.Management.entity.DangKyTinChi;
import com.coding.university_management.University.Management.entity.KiHoc;
import com.coding.university_management.University.Management.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangKyTinChiRepository extends JpaRepository<DangKyTinChi, String> {
    List<DangKyTinChi> findByKiHoc(KiHoc kiHoc);
    List<DangKyTinChi> findBySinhVien(SinhVien sinhVien);
}
