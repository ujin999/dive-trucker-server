package com.trucker.application.gas.station.dto.opinet.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OpinetDetailOilPriceDto {
    @JacksonXmlProperty(localName = "PRODCD")
    private String prodCd;
    @JacksonXmlProperty(localName = "PRICE")
    private int price;
}
