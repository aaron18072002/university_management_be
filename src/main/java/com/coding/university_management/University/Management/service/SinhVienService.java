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
    NganhHocRepository nganhHocRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    SinhVienMapper sinhVienMapper;
    PasswordEncoder passwordEncoder;

    @Transactional
    public SinhVienResponse create(SinhVienCreateRequest request) {
        String maSinhVien = "SV-" + UUID.randomUUID().toString().substring(0, 8);

        NganhHoc nganhHoc = nganhHocRepository.findById(request.getMaNganhHoc())
                .orElseThrow(() -> new IllegalArgumentException("NganhHoc not found"));

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

        SinhVien sinhVien = sinhVienMapper.toEntity(request);
        sinhVien.setMaSinhVien(maSinhVien);
        sinhVien.setNganhHoc(nganhHoc);
        sinhVien.setUser(user);

        sinhVien = sinhVienRepository.save(sinhVien);

        return sinhVienMapper.toResponse(sinhVien);
    }

    public List<SinhVienResponse> getAll() {
        return sinhVienRepository.findAll().stream()
                .map(sinhVienMapper::toResponse)
                .collect(Collectors.toList());
    }
}
