package com.trucker.application.port_terminal.dto.pnitl;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class VesselScheduleDto {
    private final String berth;                 // 선석
    private final String opr;                   // 선사
    private final String vvd;                   // 모선항차
    private final String lineVvd;               // 선사항차
    private final String headBridgeStern;       // Head(Bridge)Stern
    private final String vesselName;            // 선명
    private final String route;                 // ROUTE
    private final LocalDateTime closingTime;    // 반입마감시한
    private final LocalDateTime atbEtb;         // 전압(예정)일시
    private final LocalDateTime atdEtd;         // 출항(예정)일시
    private final String dis;                   // 양하
    private final String load;                  // 적하
    private final String shift;                 // Shift
    private final String amp;                   // AMP
    private final String status;                // 상태
}
