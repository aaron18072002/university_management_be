package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.DangKyTinChiRequest;
import com.coding.university_management.University.Management.dto.response.DangKyTinChiResponse;
import com.coding.university_management.University.Management.entity.DangKyTinChi;
import com.coding.university_management.University.Management.entity.KiHoc;
import com.coding.university_management.University.Management.entity.SinhVien;
import com.coding.university_management.University.Management.mapper.DangKyTinChiMapper;
import com.coding.university_management.University.Management.repository.DangKyTinChiRepository;
import com.coding.university_management.University.Management.repository.KiHocRepository;
import com.coding.university_management.University.Management.repository.SinhVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DangKyTinChiService {

    private final DangKyTinChiRepository dangKyTinChiRepository;
    private final DangKyTinChiMapper dangKyTinChiMapper;
    private final SinhVienRepository sinhVienRepository;
    private final KiHocRepository kiHocRepository;

    @Autowired
    public DangKyTinChiService(
            DangKyTinChiRepository dangKyTinChiRepository,
            DangKyTinChiMapper dangKyTinChiMapper,
            SinhVienRepository sinhVienRepository,
            KiHocRepository kiHocRepository) {
        this.dangKyTinChiRepository = dangKyTinChiRepository;
        this.dangKyTinChiMapper = dangKyTinChiMapper;
        this.sinhVienRepository = sinhVienRepository;
        this.kiHocRepository = kiHocRepository;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'SINHVIEN')")  // Allow both ADMIN and SINHVIEN roles
    public DangKyTinChiResponse create(DangKyTinChiRequest request) {
        DangKyTinChi entity = dangKyTinChiMapper.toEntity(request);
        DangKyTinChi savedEntity = dangKyTinChiRepository.save(entity);
        return dangKyTinChiMapper.toResponse(savedEntity);
    }

    public List<DangKyTinChiResponse> getAll() {
        List<DangKyTinChi> entities = dangKyTinChiRepository.findAll();
        return dangKyTinChiMapper.toResponseList(entities);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<DangKyTinChiResponse> getByKiHoc(String maKiHoc) {
        KiHoc kiHoc = kiHocRepository.findById(maKiHoc)
                .orElseThrow(() -> new RuntimeException("Kỳ học không tồn tại"));
        List<DangKyTinChi> entities = dangKyTinChiRepository.findByKiHoc(kiHoc);
        return dangKyTinChiMapper.toResponseList(entities);
    }

    public List<DangKyTinChiResponse> getBySinhVien(String maSinhVien) {
        SinhVien sinhVien = sinhVienRepository.findById(maSinhVien)
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));
        List<DangKyTinChi> entities = dangKyTinChiRepository.findBySinhVien(sinhVien);
        return dangKyTinChiMapper.toResponseList(entities);
    }
}
