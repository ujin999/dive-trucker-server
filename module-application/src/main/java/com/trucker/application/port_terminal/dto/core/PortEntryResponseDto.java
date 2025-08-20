package com.trucker.application.port_terminal.dto.core;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PortEntryResponseDto {
    private final LocalDateTime closingTime;
    private final String yardVehicleImport;
    private final String truckTurnTimeImport;
    private final String yardStatus;
}
