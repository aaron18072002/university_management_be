package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.MonHocCreateRequest;
import com.coding.university_management.University.Management.dto.response.MonHocResponse;
import com.coding.university_management.University.Management.dto.response.NganhHocResponse;
import com.coding.university_management.University.Management.entity.MonHoc;
import com.coding.university_management.University.Management.entity.NganhHoc;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MonHocMapper {

    public MonHoc toEntity(MonHocCreateRequest req) {
        if (req == null) return null;

        return MonHoc.builder()
                .maMonHoc(req.getMaMonHoc())
                .tenMonHoc(req.getTenMonHoc())
                .moTa(req.getMoTa())
                .build();
    }

    public MonHocResponse toResponse(MonHoc monHoc) {
        if (monHoc == null) return null;

        return MonHocResponse.builder()
                .id(monHoc.getMaMonHoc())
                .tenMonHoc(monHoc.getTenMonHoc())
                .moTa(monHoc.getMoTa())
                .maMonHocTienQuyet(monHoc.getMonHocTienQuyet() != null ?
                        monHoc.getMonHocTienQuyet().getMaMonHoc() : null)
                .nganhHocs(monHoc.getNganhHocs().stream()
                        .map(this::toNganhHocResponse)
                        .collect(Collectors.toSet()))
                .build();
    }

    public NganhHocResponse toNganhHocResponse(NganhHoc nganhHoc) {
        if (nganhHoc == null) return null;

        return NganhHocResponse.builder()
                .maNganhHoc(nganhHoc.getMaNganhHoc())
                .tenNganhHoc(nganhHoc.getTenNganhHoc())
                .moTa(nganhHoc.getMoTa())
                .build();
    }

}