package com.trucker.application.port_terminal.service;

import com.trucker.application.port_terminal.dto.core.PortDepartureResponseDto;
import com.trucker.application.port_terminal.dto.core.PortEntryResponseDto;
import com.trucker.application.port_terminal.dto.pnitl.VesselScheduleDto;
import com.trucker.application.port_terminal.dto.pnitl.YardCongestionDto;
import com.trucker.application.port_terminal.util.PnitlAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PortTerminalService {
    private final PnitlAPI pnitlAPI;
    private static final String TEMP_VESSEL_NAME = "MSC QINGDAO F";

    // NOTE: 사용자가 많아지면 요청할 때마다 크롤링하지 말고 스케쥴링을 통해 몇 분마다 크롤링하고 캐싱하고 있어야 함
    public PortEntryResponseDto getEntryInfo() {
        try {
            CompletableFuture<YardCongestionDto> yardFuture =
                    CompletableFuture.supplyAsync(pnitlAPI::scrapeYardCongestion);

            CompletableFuture<VesselScheduleDto> vesselFuture =
                    CompletableFuture.supplyAsync(() -> pnitlAPI.scrapeVesselSchedule(TEMP_VESSEL_NAME));

            CompletableFuture.allOf(yardFuture, vesselFuture).join();

            YardCongestionDto yardData = yardFuture.get();
            VesselScheduleDto vesselData = vesselFuture.get();

            if (yardData == null || vesselData == null) {
                return null;
            }

            return PortEntryResponseDto.builder()
                    .closingTime(vesselData.getClosingTime())
                    .yardVehicleImport(yardData.getYardVehicleImport())
                    .truckTurnTimeImport(yardData.getTruckTurnTimeImport())
                    .yardStatus(yardData.getYardStatus())
                    .build();
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            System.out.println("비동기 작업 처리 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    public PortDepartureResponseDto getDepartureInfo() {
        YardCongestionDto yardData = pnitlAPI.scrapeYardCongestion();
        if (yardData == null) {
            return null;
        }

        return PortDepartureResponseDto.builder()
                .yardVehicleExport(yardData.getYardVehicleExport())
                .truckTurnTimeExport(yardData.getTruckTurnTimeExport())
                .yardStatus(yardData.getYardStatus())
                .build();
    }
}
