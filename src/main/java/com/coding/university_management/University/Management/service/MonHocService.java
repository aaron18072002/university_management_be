package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.MonHocCreateRequest;
import com.coding.university_management.University.Management.dto.request.TinChiCreateRequest;
import com.coding.university_management.University.Management.dto.response.MonHocResponse;
import com.coding.university_management.University.Management.entity.LoaiTinChi;
import com.coding.university_management.University.Management.entity.MonHoc;
import com.coding.university_management.University.Management.entity.NganhHoc;
import com.coding.university_management.University.Management.entity.TinChi;
import com.coding.university_management.University.Management.enums.TenTinChi;
import com.coding.university_management.University.Management.mapper.MonHocMapper;
import com.coding.university_management.University.Management.repository.LoaiTinChiRepository;
import com.coding.university_management.University.Management.repository.MonHocRepository;
import com.coding.university_management.University.Management.repository.NganhHocRepository;
import com.coding.university_management.University.Management.repository.TinChiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonHocService {

    private final MonHocRepository monHocRepository;
    private final NganhHocRepository nganhHocRepository;
    private final TinChiRepository tinChiRepository;
    private final LoaiTinChiRepository loaiTinChiRepository;
    private final MonHocMapper monHocMapper;

    @Transactional
    public MonHocResponse createMonHoc(MonHocCreateRequest request) {
        // Generate ID if not provided
        if (request.getMaMonHoc() == null || request.getMaMonHoc().isBlank()) {
            request.setMaMonHoc(UUID.randomUUID().toString());
        }

        // Check if ID already exists
        if (monHocRepository.existsById(request.getMaMonHoc())) {
            throw new IllegalArgumentException("maMonHoc already exists");
        }

        // Use mapper to create entity
        MonHoc monHoc = monHocMapper.toEntity(request);
        monHoc.setMaMonHoc(request.getMaMonHoc());

        // Initialize collections
        if (monHoc.getNganhHocs() == null) {
            monHoc.setNganhHocs(new HashSet<>());
        }

        if (monHoc.getTinChis() == null) {
            monHoc.setTinChis(new HashSet<>());
        }

        // Set prerequisite if specified
        if (request.getMaMonHocTienQuyet() != null && !request.getMaMonHocTienQuyet().isBlank()) {
            MonHoc pre = monHocRepository.findById(request.getMaMonHocTienQuyet())
                    .orElseThrow(() -> new IllegalArgumentException("Prerequisite not found"));
            monHoc.setMonHocTienQuyet(pre);
        }

        // Save entity first to get ID
        monHoc = monHocRepository.save(monHoc);

        // Link with majors
        if (request.getMaNganhHocs() != null && !request.getMaNganhHocs().isEmpty()) {
            List<NganhHoc> majors = nganhHocRepository.findAllById(request.getMaNganhHocs());
            if (majors.size() != request.getMaNganhHocs().size()) {
                throw new IllegalArgumentException("Some maNganhHoc not found");
            }

            for (NganhHoc nh : majors) {
                // Initialize the collection if null
                if (nh.getMonHocs() == null) {
                    nh.setMonHocs(new HashSet<>());
                }
                // Update both sides of the relationship
                nh.getMonHocs().add(monHoc);
                monHoc.getNganhHocs().add(nh);
            }
            // Save both sides
            nganhHocRepository.saveAll(majors);
            monHocRepository.save(monHoc);
        }

        // Refresh entity to ensure all relationships are loaded
        MonHoc finalMonHoc = monHocRepository.findById(monHoc.getMaMonHoc()).orElse(monHoc);

        // Use the mapper implementation to create response
        return monHocMapper.toResponse(finalMonHoc);
    }

    public List<MonHocResponse> getAllMonHoc() {
        List<MonHoc> monHocs = monHocRepository.findAll();
        return monHocs.stream()
                .map(monHocMapper::toResponse)
                .collect(Collectors.toList());
    }

}
