package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.DangKyTinChiRequest;
import com.coding.university_management.University.Management.dto.response.DangKyTinChiResponse;
import com.coding.university_management.University.Management.entity.DangKyTinChi;
import com.coding.university_management.University.Management.entity.KiHoc;
import com.coding.university_management.University.Management.entity.SinhVien;
import com.coding.university_management.University.Management.entity.TinChi;
import com.coding.university_management.University.Management.repository.KiHocRepository;
import com.coding.university_management.University.Management.repository.SinhVienRepository;
import com.coding.university_management.University.Management.repository.TinChiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DangKyTinChiMapper {

    private final SinhVienRepository sinhVienRepository;
    private final TinChiRepository tinChiRepository;
    private final KiHocRepository kiHocRepository;
    private final SinhVienMapper sinhVienMapper;
    private final TinChiMapper tinChiMapper;
    private final KiHocMapper kiHocMapper;

    @Autowired
    public DangKyTinChiMapper(SinhVienRepository sinhVienRepository,
                              TinChiRepository tinChiRepository,
                              KiHocRepository kiHocRepository,
                              SinhVienMapper sinhVienMapper,
                              TinChiMapper tinChiMapper,
                              KiHocMapper kiHocMapper) {
        this.sinhVienRepository = sinhVienRepository;
        this.tinChiRepository = tinChiRepository;
        this.kiHocRepository = kiHocRepository;
        this.sinhVienMapper = sinhVienMapper;
        this.tinChiMapper = tinChiMapper;
        this.kiHocMapper = kiHocMapper;
    }

    public DangKyTinChi toEntity(DangKyTinChiRequest request) {
        SinhVien sinhVien = sinhVienRepository.findById(request.getMaSinhVien())
                .orElseThrow(() -> new RuntimeException("Sinh viên không tồn tại"));

        TinChi tinChi = tinChiRepository.findById(request.getMaTinChi())
                .orElseThrow(() -> new RuntimeException("Tín chỉ không tồn tại"));

        KiHoc kiHoc = kiHocRepository.findById(request.getMaKiHoc())
                .orElseThrow(() -> new RuntimeException("Kỳ học không tồn tại"));

        return DangKyTinChi.builder()
                .sinhVien(sinhVien)
                .tinChi(tinChi)
                .kiHoc(kiHoc)
                .trangThai(request.getTrangThai())
                .ngayDangKy(request.getNgayDangKy() != null ? request.getNgayDangKy() : LocalDate.now())
                .build();
    }

    public DangKyTinChiResponse toResponse(DangKyTinChi entity) {
        return DangKyTinChiResponse.builder()
                .id(entity.getId())
                .sinhVien(sinhVienMapper.toResponse(entity.getSinhVien()))
                .tinChi(tinChiMapper.toResponse(entity.getTinChi()))
                .kiHoc(kiHocMapper.toResponse(entity.getKiHoc()))
                .trangThai(entity.getTrangThai())
                .ngayDangKy(entity.getNgayDangKy())
                .build();
    }

    public List<DangKyTinChiResponse> toResponseList(List<DangKyTinChi> entities) {
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
