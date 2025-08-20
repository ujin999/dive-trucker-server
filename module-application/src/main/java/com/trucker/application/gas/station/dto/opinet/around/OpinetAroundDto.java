package com.trucker.application.gas.station.dto.opinet.around;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OpinetAroundDto {
    @JsonProperty("UNI_ID")
    private String uniId;
    @JsonProperty("POLL_DIV_CO")
    private String pollDivCd;
    @JsonProperty("OS_NM")
    private String osNm;
    @JsonProperty("PRICE")
    private int price;
    @JsonProperty("DISTANCE")
    private double distance;
    @JsonProperty("GIS_X_COOR")
    private double gisXCoor;
    @JsonProperty("GIS_Y_COOR")
    private double gisYCoor;
}
