package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.TinChiCreateRequest;
import com.coding.university_management.University.Management.dto.response.MonHocResponse;
import com.coding.university_management.University.Management.dto.response.NganhHocResponse;
import com.coding.university_management.University.Management.dto.response.TinChiResponse;
import com.coding.university_management.University.Management.entity.LoaiTinChi;
import com.coding.university_management.University.Management.entity.MonHoc;
import com.coding.university_management.University.Management.entity.TinChi;
import com.coding.university_management.University.Management.enums.TenTinChi;
import com.coding.university_management.University.Management.mapper.TinChiMapper;
import com.coding.university_management.University.Management.repository.LoaiTinChiRepository;
import com.coding.university_management.University.Management.repository.MonHocRepository;
import com.coding.university_management.University.Management.repository.TinChiRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TinChiService {

    TinChiRepository tinChiRepository;
    LoaiTinChiRepository loaiTinChiRepository;
    MonHocRepository monHocRepository;
    TinChiMapper tinChiMapper;

    @Transactional
    public TinChiResponse create(TinChiCreateRequest req) {
        LoaiTinChi loai = loaiTinChiRepository.findById(req.getMaLoaiTinChi())
                .orElseThrow(() -> new IllegalArgumentException("LoaiTinChi not found"));

        MonHoc monHoc = monHocRepository.findById(req.getMaMonHoc())
                .orElseThrow(() -> new IllegalArgumentException("MonHoc not found"));

        // Initialize collections if null
        if (loai.getTinChis() == null) {
            loai.setTinChis(new HashSet<>());
        }

        if (monHoc.getTinChis() == null) {
            monHoc.setTinChis(new HashSet<>());
        }

        TinChi tinChi = TinChi.builder()
                .soTinChi(req.getSoTinChi())
                .giaTriTinChi(req.getGiaTriTinChi())
                .loaiTinChi(loai)
                .monHoc(monHoc)
                .tenTinChi(TenTinChi.valueOf(req.getTenTinChi().toUpperCase()))
                .build();

        tinChi = tinChiRepository.save(tinChi);

        // maintain owning-side collections
        loai.getTinChis().add(tinChi);
        monHoc.getTinChis().add(tinChi);

        // Save the updated entities
        loaiTinChiRepository.save(loai);
        monHocRepository.save(monHoc);

        // Use the mapper to create response with proper NganhHoc mapping
        return tinChiMapper.toResponse(tinChi);
    }

    public List<TinChiResponse> getAll() {
        List<TinChi> tinChis = tinChiRepository.findAll();
        return tinChis.stream()
                .map(tinChiMapper::toResponse)
                .collect(Collectors.toList());
    }
}

