package com.trucker.application.location.controller;

import com.trucker.application.location.dto.PickUpLocationRequestDto;
import com.trucker.application.location.dto.PickUpLocationResponseDto;
import com.trucker.application.location.service.PickUpLocationService;
import com.trucker.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pick-up-locations")
@RequiredArgsConstructor
public class PickUpLocationController {
    private final PickUpLocationService pickUpLocationService;

    @PostMapping
    public ResponseEntity<ApiResponse<PickUpLocationResponseDto>> registerPickUpLocation(@Valid @RequestBody PickUpLocationRequestDto dto) {
        PickUpLocationResponseDto responseDto = pickUpLocationService.registerPickUpLocation(dto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PickUpLocationResponseDto>> getPickUpLocationById(@PathVariable Long id) {
        PickUpLocationResponseDto responseDto = pickUpLocationService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }
}
