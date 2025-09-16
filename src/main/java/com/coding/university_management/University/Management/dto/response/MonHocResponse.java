package com.coding.university_management.University.Management.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonHocResponse {

    private String id;
    private String tenMonHoc;
    private String moTa;
    private Integer tongSoTinChi;
    private String maMonHocTienQuyet;
    private Set<String> maNganhHocs;
    private List<TinChiResponse> tinChis;

}
