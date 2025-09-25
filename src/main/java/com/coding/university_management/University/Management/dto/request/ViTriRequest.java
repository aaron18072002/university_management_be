package com.coding.university_management.University.Management.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViTriRequest {

    private String tenViTri;
    private String moTa;
    private BigDecimal mucLuongCoBan;

}
