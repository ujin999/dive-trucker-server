package com.trucker.application.location.controller;

import com.trucker.application.location.dto.DropOffLocationRequestDto;
import com.trucker.application.location.dto.DropOffLocationResponseDto;
import com.trucker.application.location.service.DropOffLocationService;
import com.trucker.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drop-off-location")
@RequiredArgsConstructor
public class DropOffLocationController {
    private final DropOffLocationService dropOffLocationService;

    @PostMapping
    public ResponseEntity<ApiResponse<DropOffLocationResponseDto>> registerDropOffLocation(@Valid @RequestBody DropOffLocationRequestDto dto) {
        DropOffLocationResponseDto responseDto = dropOffLocationService.registerDropOffLocation(dto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DropOffLocationResponseDto>> getDropOffLocationFindById(@PathVariable Long id) {
        DropOffLocationResponseDto responseDto = dropOffLocationService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
}
