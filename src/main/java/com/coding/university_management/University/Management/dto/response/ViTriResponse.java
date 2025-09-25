package com.coding.university_management.University.Management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViTriResponse {
    private String maViTri;
    private String tenViTri;
    private String moTa;
    private BigDecimal mucLuongCoBan;
}
