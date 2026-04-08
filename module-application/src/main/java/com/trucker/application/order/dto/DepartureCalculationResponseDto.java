package com.trucker.application.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DepartureCalculationResponseDto {
    LocalDateTime optimalDepartureTime;
}
