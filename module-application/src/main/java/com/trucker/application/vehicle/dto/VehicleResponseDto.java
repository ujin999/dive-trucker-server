package com.trucker.application.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponseDto {
    private Long id;
    private Integer carType;
    private String licensePlate;
    private Boolean hasTemperatureControl;
    private String cargobinLength;
    private String cargobinWidth;
    private Integer fuelType;
}
