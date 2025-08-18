package com.trucker.application.vehicle.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleRequestDto {
    @Min(value = 0, message = "carType must be greater than or equal to 0")
    @Max(value = 6, message = "carType must be less than or equal to 6")
    @NotNull(message = "car type cannot be null.")
    private Integer carType;

    @NotBlank(message = "License plate cannot be blank.")
    private String licensePlate;

    @NotNull(message = "Temperature control status cannot be null.")
    private Boolean hasTemperatureControl;

    @NotBlank(message = "Cargobin length cannot be blank.")
    private String cargobinLength;

    private String cargobinWidth;

    @Min(value = 0, message = "fuel type must be greater than or equal to 0")
    @Max(value = 7, message = "fuel type must be less than or equal to 7")
    @NotNull(message = "Fuel type cannot be null.")
    private Integer fuelType;
}
