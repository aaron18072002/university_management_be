package com.coding.university_management.University.Management.controller;

import com.coding.university_management.University.Management.dto.request.ViTriRequest;
import com.coding.university_management.University.Management.dto.response.ViTriResponse;
import com.coding.university_management.University.Management.service.ViTriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vi-tri")
@RequiredArgsConstructor
public class ViTriController {

    private final ViTriService viTriService;

    @GetMapping("/{id}")
    public ResponseEntity<ViTriResponse> getById(@PathVariable("id") String maViTri) {
        return ResponseEntity.ok(viTriService.getById(maViTri));
    }

    @GetMapping
    public ResponseEntity<List<ViTriResponse>> getAll() {
        return ResponseEntity.ok(viTriService.getAll());
    }

    @PostMapping
    public ResponseEntity<ViTriResponse> create(@RequestBody ViTriRequest request) {
        return new ResponseEntity<>(viTriService.create(request), HttpStatus.CREATED);
    }
}
