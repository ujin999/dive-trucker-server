package com.trucker.application.location.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PickUpLocationRequestDto {
    @NotNull(message = "Location ID cannot be null.")
    private Long locationId;
}
