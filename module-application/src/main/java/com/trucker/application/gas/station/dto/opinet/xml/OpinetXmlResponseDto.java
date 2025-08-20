package com.trucker.application.gas.station.dto.opinet.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.trucker.application.gas.station.dto.opinet.around.OpinetAroundDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "RESULT") // <RESULT> 태그에 매핑
public class OpinetXmlResponseDto {
    @JacksonXmlProperty(localName = "OIL") // <OIL> 태그에 매핑
    @JacksonXmlElementWrapper(useWrapping = false) // <OIL> 태그가 여러 개 있을 수 있으므로 리스트로 받음
    private List<OpinetAroundDto> oil;
}
