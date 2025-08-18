package com.trucker.application.vehicle.controller;

import com.trucker.application.vehicle.dto.VehiclePatchDto;
import com.trucker.application.vehicle.dto.VehicleRequestDto;
import com.trucker.application.vehicle.dto.VehicleResponseDto;
import com.trucker.application.vehicle.service.VehicleService;
import com.trucker.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    // TODO: After implement authentication, must fix it!
    static Long TEMP_USER_ID = 6L;

    // /api/vehicles
    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponseDto>> registerVehicle(
            @Valid @RequestBody VehicleRequestDto createDto) {
        VehicleResponseDto createdVehicle = vehicleService.registerVehicle(createDto, TEMP_USER_ID);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdVehicle));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<VehicleResponseDto>>> findAllVehicles() {
        List<VehicleResponseDto> vehicles = vehicleService.findAllVehiclesByUserId(TEMP_USER_ID);
        return ResponseEntity.ok(ApiResponse.success(vehicles));
    }

    // /api/vehicles/{vehicleId}
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponseDto>> updateVehicle(
            @PathVariable Long id, @Valid @RequestBody VehicleRequestDto dto) {

        VehicleResponseDto responseDto = vehicleService.updateVehicle(id, dto, TEMP_USER_ID);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponseDto>> getVehicleById(@PathVariable Long id) {
        VehicleResponseDto responseDto = vehicleService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<VehicleResponseDto>> patchVehicle(
            @PathVariable Long id, @RequestBody VehiclePatchDto dto) {

        VehicleResponseDto responseDto = vehicleService.patchVehicle(id, dto);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
