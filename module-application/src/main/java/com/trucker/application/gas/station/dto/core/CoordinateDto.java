package com.trucker.application.gas.station.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateDto {
    private Double x;
    private Double y;
}
