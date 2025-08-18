package com.trucker.application.vehicle.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehiclePatchDto {
    private Integer carType;
    private String licensePlate;
    private Boolean hasTemperatureControl;
    private String cargobinLength;
    private String cargobinWidth;
    private Integer fuelType;
}
