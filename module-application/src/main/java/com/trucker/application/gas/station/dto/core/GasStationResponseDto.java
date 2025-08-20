package com.trucker.application.gas.station.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GasStationResponseDto {
    private String name;
    private String oldAddress;
    private String newAddress;
    private int oilPrice;
    private CoordinateDto coordinate;
    private OilInfoDto oil;
}
