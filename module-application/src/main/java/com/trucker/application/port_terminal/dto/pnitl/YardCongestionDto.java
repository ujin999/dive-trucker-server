package com.trucker.application.port_terminal.dto.pnitl;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YardCongestionDto {
    private final String yardVehicleImport;
    private final String yardVehicleExport;
    private final String truckTurnTimeImport;
    private final String truckTurnTimeExport;
    private final String yardStatus;
}
