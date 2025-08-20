package com.trucker.application.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDto {
    @NotBlank(message = "City cannot be blank.")
    private String city;

    @NotBlank(message = "District cannot be blank.")
    private String district;

    @NotBlank(message = "Address detail cannot be blank.")
    private String addressDetail;

    @NotNull(message = "Latitude cannot be null.")
    private Double latitude;

    @NotNull(message = "Longtitude cannot be null.")
    private Double longitude;
}
