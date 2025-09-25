package com.coding.university_management.University.Management.repository;


import com.coding.university_management.University.Management.entity.ViTri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViTriRepository extends JpaRepository<ViTri, String> {
}
