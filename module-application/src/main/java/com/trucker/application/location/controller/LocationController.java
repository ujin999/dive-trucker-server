package com.trucker.application.location.controller;

import com.trucker.application.location.dto.LocationRequestDto;
import com.trucker.application.location.dto.LocationResponseDto;
import com.trucker.application.location.entity.Location;
import com.trucker.application.location.service.LocationService;
import com.trucker.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationResponseDto>>> getAllLocations() {
        List<LocationResponseDto> locations = locationService.findAllLocations();
        return ResponseEntity.ok(ApiResponse.success(locations));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<LocationResponseDto>> registerLocation(@Valid @RequestBody LocationRequestDto dto) {
        LocationResponseDto responseDto = locationService.registerLocation(dto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponseDto>> getLocationById(@PathVariable Long id) {
        LocationResponseDto responseDto = locationService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<LocationResponseDto>> updateLocation(
            @PathVariable Long id, @Valid @RequestBody LocationRequestDto dto) {

        LocationResponseDto responseDto = locationService.updateLocation(id, dto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
