package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.ViTriRequest;
import com.coding.university_management.University.Management.dto.response.ViTriResponse;
import com.coding.university_management.University.Management.entity.ViTri;
import org.springframework.stereotype.Component;

@Component
public class ViTriMapper {

    public ViTriResponse toResponse(ViTri viTri) {
        if (viTri == null) {
            return null;
        }

        return ViTriResponse.builder()
                .maViTri(viTri.getMaViTri())
                .tenViTri(viTri.getTenViTri())
                .moTa(viTri.getMoTa())
                .mucLuongCoBan(viTri.getMucLuongCoBan())
                .build();
    }

    public ViTri toEntity(ViTriRequest request) {
        if (request == null) {
            return null;
        }

        return ViTri.builder()
                .tenViTri(request.getTenViTri())
                .moTa(request.getMoTa())
                .mucLuongCoBan(request.getMucLuongCoBan())
                .build();
    }
}
