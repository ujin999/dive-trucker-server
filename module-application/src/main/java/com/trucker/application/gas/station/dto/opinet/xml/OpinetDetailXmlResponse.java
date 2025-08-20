package com.trucker.application.gas.station.dto.opinet.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "RESULT")
public class OpinetDetailXmlResponse {
    @JacksonXmlProperty(localName = "OIL") // <OIL> 태그
    private OpinetDetailOilDto oil;
}
