package com.trucker.application.port_terminal.dto.core;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PortDepartureResponseDto {
    private final String yardVehicleExport;
    private final String truckTurnTimeExport;
    private final String yardStatus;
}
