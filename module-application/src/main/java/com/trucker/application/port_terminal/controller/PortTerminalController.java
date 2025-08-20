package com.trucker.application.port_terminal.controller;

import com.trucker.application.port_terminal.dto.core.PortDepartureResponseDto;
import com.trucker.application.port_terminal.dto.core.PortEntryResponseDto;
import com.trucker.application.port_terminal.service.PortTerminalService;
import com.trucker.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/port-terminal")
@RequiredArgsConstructor
public class PortTerminalController {
    private final PortTerminalService portTerminalService;

    @GetMapping("/entry-info")
    public ResponseEntity<ApiResponse<PortEntryResponseDto>> getPortEntryInfo() {
        PortEntryResponseDto response = portTerminalService.getEntryInfo();
        if (response == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/departure-info")
    public ResponseEntity<ApiResponse<PortDepartureResponseDto>> getPortDepartureInfo() {
        PortDepartureResponseDto response = portTerminalService.getDepartureInfo();
        if (response == null) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
