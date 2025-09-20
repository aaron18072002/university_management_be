package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.MonHocCreateRequest;
import com.coding.university_management.University.Management.dto.response.MonHocResponse;
import com.coding.university_management.University.Management.dto.response.TinChiResponse;
import com.coding.university_management.University.Management.entity.MonHoc;
import com.coding.university_management.University.Management.entity.TinChi;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MonHocMapper {

    @Mapping(target = "monHocTienQuyet", ignore = true)
    @Mapping(target = "monHocPhuThuoc", ignore = true)
    @Mapping(target = "tinChis", ignore = true)
    @Mapping(target = "ketQuaHocTaps", ignore = true)
    @Mapping(target = "hocLais", ignore = true)
    @Mapping(target = "nganhHocs", ignore = true)
    MonHoc toEntity(MonHocCreateRequest req);

    default MonHocResponse toResponse(MonHoc monHoc) {
        return MonHocResponse.builder()
                .id(monHoc.getMaMonHoc())
                .tenMonHoc(monHoc.getTenMonHoc())
                .moTa(monHoc.getMoTa())
//                .tongSoTinChi(monHoc.getTongSoTinChi())
                .maMonHocTienQuyet(monHoc.getMonHocTienQuyet() != null ? monHoc.getMonHocTienQuyet().getMaMonHoc() : null)
                .maNganhHocs(monHoc.getNganhHocs().stream()
                        .map(m -> m.getMaNganhHoc())
                        .collect(Collectors.toSet()))
                .tinChis(monHoc.getTinChis().stream().map(this::toTinChiResponse).toList())
                .build();
    }

    default TinChiResponse toTinChiResponse(TinChi tc) {
        return TinChiResponse.builder()
                .soTinChi(tc.getSoTinChi())
                .giaTriTinChi(tc.getGiaTriTinChi())
                .tenTinChi(tc.getTenTinChi().name())
                .maLoaiTinChi(tc.getLoaiTinChi() != null ? tc.getLoaiTinChi().getMaLoaiTinChi() : null)
                .build();
    }

}