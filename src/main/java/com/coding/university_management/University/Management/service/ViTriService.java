package com.coding.university_management.University.Management.service;

import com.coding.university_management.University.Management.dto.request.ViTriRequest;
import com.coding.university_management.University.Management.dto.response.ViTriResponse;
import com.coding.university_management.University.Management.entity.ViTri;
import com.coding.university_management.University.Management.exception.NotFoundException;
import com.coding.university_management.University.Management.mapper.ViTriMapper;
import com.coding.university_management.University.Management.repository.ViTriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViTriService {

    private final ViTriRepository viTriRepository;
    private final ViTriMapper viTriMapper;

    public ViTriResponse getById(String maViTri) {
        ViTri viTri = viTriRepository.findById(maViTri)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy vị trí với mã: " + maViTri));
        return viTriMapper.toResponse(viTri);
    }

    public List<ViTriResponse> getAll() {
        return viTriRepository.findAll()
                .stream()
                .map(viTriMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ViTriResponse create(ViTriRequest request) {
        ViTri viTri = viTriMapper.toEntity(request);
        ViTri savedViTri = viTriRepository.save(viTri);
        return viTriMapper.toResponse(savedViTri);
    }
}
