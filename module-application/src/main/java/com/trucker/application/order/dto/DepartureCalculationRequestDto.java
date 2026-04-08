package com.trucker.application.order.dto;

import com.trucker.application.location.dto.CoordinatesDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DepartureCalculationRequestDto {
    private CoordinatesDto driverCoords;
    private LocalDateTime requiredArrivalTime;
}
