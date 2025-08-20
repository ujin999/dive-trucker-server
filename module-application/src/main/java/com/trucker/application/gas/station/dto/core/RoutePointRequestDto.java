package com.trucker.application.gas.station.dto.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoutePointRequestDto {
    private Double x;
    private Double y;
}
