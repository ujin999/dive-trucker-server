package com.trucker.application.gas.station.dto.opinet.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OpinetDetailResultDto {
    @JsonProperty("OIL")
    private List<OpinetDetailDto> oil;
}
