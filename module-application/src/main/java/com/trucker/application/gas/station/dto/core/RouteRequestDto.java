package com.trucker.application.gas.station.dto.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteRequestDto {
    private List<RoutePointRequestDto> route;
}
