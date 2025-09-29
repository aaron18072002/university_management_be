package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.TinChiCreateRequest;
import com.coding.university_management.University.Management.dto.response.TinChiResponse;
import com.coding.university_management.University.Management.entity.TinChi;
import com.coding.university_management.University.Management.enums.TenTinChi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TinChiMapper {

    private final MonHocMapper monHocMapper;

    public TinChi toEntity(TinChiCreateRequest request) {
        if (request == null) return null;

        return TinChi.builder()
                .soTinChi(request.getSoTinChi())
                .giaTriTinChi(request.getGiaTriTinChi())
                .tenTinChi(TenTinChi.valueOf(request.getTenTinChi().toUpperCase()))
                .build();
    }

    public TinChiResponse toResponse(TinChi tinChi) {
        if (tinChi == null) return null;

        return TinChiResponse.builder()
                .maTinChi(tinChi.getMaTinChi())
                .soTinChi(tinChi.getSoTinChi())
                .giaTriTinChi(tinChi.getGiaTriTinChi())
                .tenTinChi(tinChi.getTenTinChi() != null ? tinChi.getTenTinChi().name() : null)
                .maLoaiTinChi(tinChi.getLoaiTinChi() != null ? tinChi.getLoaiTinChi().getMaLoaiTinChi() : null)
                .monHoc(monHocMapper.toResponse(tinChi.getMonHoc()))  // This will map NganhHoc properly
                .build();
    }
}
