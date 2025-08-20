package com.trucker.application.gas.station.dto.opinet.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpinetDetailResponseDto {
    @JsonProperty("RESULT")
    private OpinetDetailResultDto result;
}
