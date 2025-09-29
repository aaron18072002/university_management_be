package com.coding.university_management.University.Management.repository;

import com.coding.university_management.University.Management.entity.ChiTietSinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietSinhVienRepository extends JpaRepository<ChiTietSinhVien, String> {
    // You can add custom query methods here if needed
}
