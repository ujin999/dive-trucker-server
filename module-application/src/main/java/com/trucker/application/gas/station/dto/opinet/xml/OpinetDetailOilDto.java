package com.trucker.application.gas.station.dto.opinet.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.trucker.application.gas.station.dto.opinet.detail.OpinetDetailOilPriceDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpinetDetailOilDto {
    @JacksonXmlProperty(localName = "UNI_ID")
    private String uniId;
    @JacksonXmlProperty(localName = "OS_NM")
    private String osNm;
    @JacksonXmlProperty(localName = "VAN_ADR")
    private String vanAdr;
    @JacksonXmlProperty(localName = "NEW_ADR")
    private String newAdr;
    @JacksonXmlProperty(localName = "GIS_X_COOR")
    private double gisXCoor;
    @JacksonXmlProperty(localName = "GIS_Y_COOR")
    private double gisYCoor;

    @JacksonXmlProperty(localName = "OIL_PRICE")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OpinetDetailOilPriceDto> oilPrice;
}
