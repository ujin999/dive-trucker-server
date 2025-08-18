package com.trucker.application.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleCreateDto {
    @NotBlank
    private String carType;
    @NotBlank
    private String licensePlate;
    @NotBlank
    private Boolean hasTemperatureControl;
    @NotBlank
    private String cargobinLength;
    private String cargobinWidth;
    @NotBlank
    private Integer fuelType;
}
