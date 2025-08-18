package com.trucker.application.vehicle.service;

import com.trucker.application.user.repository.UserRepository;
import com.trucker.application.vehicle.dto.VehiclePatchDto;
import com.trucker.application.vehicle.dto.VehicleRequestDto;
import com.trucker.application.vehicle.dto.VehicleResponseDto;
import com.trucker.application.vehicle.entity.Vehicle;
import com.trucker.application.vehicle.repository.VehicleRepository;
import com.trucker.common.domain.user.entity.User;
import com.trucker.core.exception.BusinessException;
import com.trucker.core.exception.ErrorCode;
import com.trucker.core.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleResponseDto registerVehicle(VehicleRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Vehicle vehicle = Vehicle.builder()
                .user(user)
                .carType(requestDto.getCarType())
                .licensePlate(requestDto.getLicensePlate())
                .hasTemperatureControl(requestDto.getHasTemperatureControl())
                .cargobinLength(requestDto.getCargobinLength())
                .cargobinWidth(requestDto.getCargobinWidth())
                .fuelType(requestDto.getFuelType())
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return new VehicleResponseDto(
                savedVehicle.getId(),
                savedVehicle.getCarType(),
                savedVehicle.getLicensePlate(),
                savedVehicle.getHasTemperatureControl(),
                savedVehicle.getCargobinLength(),
                savedVehicle.getCargobinWidth(),
                savedVehicle.getFuelType()
        );
    }

    public List<VehicleResponseDto> findAllVehiclesByUserId(Long userId) {
        return vehicleRepository.findByUserId(userId).stream()
                .map(v -> new VehicleResponseDto(v.getId(), v.getCarType(), v.getLicensePlate(),
                        v.getHasTemperatureControl(), v.getCargobinLength(),
                        v.getCargobinWidth(), v.getFuelType()))
                .collect(Collectors.toList());
    }

    public VehicleResponseDto updateVehicle(Long id, VehicleRequestDto requestDto, Long userId) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        vehicle.update(user, requestDto.getCarType(), requestDto.getLicensePlate(),
                requestDto.getHasTemperatureControl(), requestDto.getCargobinLength(),
                requestDto.getCargobinWidth(), requestDto.getFuelType());

        return convertToResponseDto(vehicle);
    }

    @Transactional
    public VehicleResponseDto patchVehicle(Long id, VehiclePatchDto patchDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        if (patchDto.getCarType() != null) {
            vehicle.updateCarType(patchDto.getCarType());
        }
        if (patchDto.getLicensePlate() != null) {
            vehicle.updateLicensePlate(patchDto.getLicensePlate());
        }
        if (patchDto.getHasTemperatureControl() != null) {
            vehicle.updateHasTemperatureControl(patchDto.getHasTemperatureControl());
        }
        if (patchDto.getCargobinLength() != null) {
            vehicle.updateCargobinLength(patchDto.getCargobinLength());
        }
        if (patchDto.getCargobinWidth() != null) {
            vehicle.updateCargobinWidth(patchDto.getCargobinWidth());
        }
        if (patchDto.getFuelType() != null) {
            vehicle.updateFuelType(patchDto.getFuelType());
        }

        return convertToResponseDto(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        vehicleRepository.deleteById(id);
    }

    public VehicleResponseDto findById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        return convertToResponseDto(vehicle);
    }

    private VehicleResponseDto convertToResponseDto(Vehicle vehicle) {
        return VehicleResponseDto.builder()
                .id(vehicle.getId())
                .carType(vehicle.getCarType())
                .licensePlate(vehicle.getLicensePlate())
                .hasTemperatureControl(vehicle.getHasTemperatureControl())
                .cargobinLength(vehicle.getCargobinLength())
                .cargobinWidth(vehicle.getCargobinWidth())
                .fuelType(vehicle.getFuelType())
                .build();
    }
}
