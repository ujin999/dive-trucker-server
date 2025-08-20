package com.trucker.application.gas.station.controller;

import com.trucker.application.gas.station.dto.core.GasStationResponseDto;
import com.trucker.application.gas.station.dto.core.RouteRequestDto;
import com.trucker.application.gas.station.service.GasStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/gas-stations")
@RequiredArgsConstructor
public class GasStationController {
    private final GasStationService gasStationService;

    @PostMapping("/by-path")
    public ResponseEntity<List<GasStationResponseDto>> getGasStationsForRoute(
            @RequestBody RouteRequestDto routeRequestDto) {

        List<GasStationResponseDto> result = gasStationService.findGasStationsByPath(routeRequestDto);
        return ResponseEntity.ok(result);
    }
}
