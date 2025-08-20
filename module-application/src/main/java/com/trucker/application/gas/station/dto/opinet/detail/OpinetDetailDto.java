package com.trucker.application.gas.station.dto.opinet.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OpinetDetailDto {
    @JsonProperty("UNI_ID")
    private String uniId;
    @JsonProperty("OS_NM")
    private String osNm;
    @JsonProperty("VAN_ADR")
    private String vanAdr;
    @JsonProperty("NEW_ADR")
    private String newAdr;
    @JsonProperty("GIS_X_COOR")
    private double gisXCoor;
    @JsonProperty("GIS_Y_COOR")
    private double gisYCoor;
    @JsonProperty("OIL_PRICE")
    private List<OpinetDetailOilPriceDto> oilPrice;
}
