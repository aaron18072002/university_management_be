package com.coding.university_management.University.Management.repository;

import com.coding.university_management.University.Management.entity.SinhVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
    // You can add custom query methods here if needed
}