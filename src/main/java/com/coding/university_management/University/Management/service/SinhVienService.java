package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.SinhVienCreateRequest;
import com.coding.university_management.University.Management.dto.response.SinhVienResponse;
import com.coding.university_management.University.Management.entity.*;
import com.coding.university_management.University.Management.mapper.SinhVienMapper;
import com.coding.university_management.University.Management.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SinhVienService {

    SinhVienRepository sinhVienRepository;
    ChiTietSinhVienRepository chiTietSinhVienRepository;
    NganhHocRepository nganhHocRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    SinhVienMapper sinhVienMapper;
    PasswordEncoder passwordEncoder;

    @Transactional
    public SinhVienResponse create(SinhVienCreateRequest request) {
        // Generate student ID
        String maSinhVien = "SV-" + UUID.randomUUID().toString().substring(0, 8);

        // Find NganhHoc
        NganhHoc nganhHoc = nganhHocRepository.findById(request.getMaNganhHoc())
                .orElseThrow(() -> new IllegalArgumentException("NganhHoc not found"));

        // Create User with SINHVIEN role
        Role sinhVienRole = roleRepository.findById("SINHVIEN")
                .orElseThrow(() -> new IllegalArgumentException("Role SINHVIEN not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(sinhVienRole);

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();

        user = userRepository.save(user);

        // First, create and save ChiTietSinhVien
        ChiTietSinhVien chiTietSinhVien = ChiTietSinhVien.builder()
                .maSinhVien(maSinhVien)  // Set ID only here
                .diaChi(request.getDiaChi())
                .ngaySinh(request.getNgaySinh())
                .gioiTinh(request.getGioiTinh())
                .quocTich(request.getQuocTich())
                .cccd(request.getCccd())
                .sdtNguoiThan(request.getSdtNguoiThan())
                .build();

        // Save ChiTietSinhVien first and flush to ensure it's in the database
        chiTietSinhVienRepository.saveAndFlush(chiTietSinhVien);

        // Now create SinhVien WITHOUT setting the ID - let @MapsId handle it
        SinhVien sinhVien = SinhVien.builder()
                // Do NOT set maSinhVien here
                .hoTen(request.getHoTen())
                .email(request.getEmail())
                .soDienThoai(request.getSoDienThoai())
                .ngayNhapHoc(request.getNgayNhapHoc() != null ? request.getNgayNhapHoc() : LocalDate.now())
                .nganhHoc(nganhHoc)
                .user(user)
                .chiTietSinhVien(chiTietSinhVien)
                .build();

        // Save SinhVien - @MapsId will automatically copy the ID
        sinhVien = sinhVienRepository.save(sinhVien);

        // Set the bidirectional relationship after both entities are saved
        chiTietSinhVien.setSinhVien(sinhVien);
        chiTietSinhVienRepository.save(chiTietSinhVien);

        return sinhVienMapper.toResponse(sinhVien);
    }

    public List<SinhVienResponse> getAll() {
        return sinhVienRepository.findAll().stream()
                .map(sinhVienMapper::toResponse)
                .collect(Collectors.toList());
    }
}
