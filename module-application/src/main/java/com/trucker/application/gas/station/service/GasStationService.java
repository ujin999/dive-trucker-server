package com.trucker.application.gas.station.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.trucker.application.gas.station.dto.core.*;
import com.trucker.application.gas.station.dto.opinet.around.OpinetAroundDto;
import com.trucker.application.gas.station.dto.opinet.detail.OpinetDetailOilPriceDto;
import com.trucker.application.gas.station.dto.opinet.xml.OpinetDetailOilDto;
import com.trucker.application.gas.station.dto.opinet.xml.OpinetDetailXmlResponse;
import com.trucker.application.gas.station.dto.opinet.xml.OpinetXmlResponseDto;
import com.trucker.application.gas.station.enums.FuelType;
import com.trucker.core.exception.BusinessException;
import com.trucker.core.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
//@RequiredArgsConstructor
public class GasStationService {
    private final WebClient webClient;

    @Value("${opinet.api.key}")
    private String apiKey;

    @Value("${opinet.api.url.around.all}")
    private String aroundApiUrl;

    @Value("${opinet.api.url.detail}")
    private String detailApiUrl;

    // TODO: 추후에 차량 정보와 연동
    static FuelType TEMP_FUEL_TYPE = FuelType.GASOLINE;

    private final XmlMapper xmlMapper;

    public GasStationService(WebClient webClient) {
        this.webClient = webClient;
        this.xmlMapper = new XmlMapper();
        // 모르는 필드가 있어도 오류를 발생시키지 않고 무시
        this.xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<GasStationResponseDto> findGasStationsByPath(RouteRequestDto requestDto) {
        if (requestDto == null || requestDto.getRoute() == null || requestDto.getRoute().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        List<RoutePointRequestDto> routePoints = requestDto.getRoute();

        List<OpinetAroundDto> top3CheapestStations = Flux.fromIterable(routePoints)
                .flatMap(this::fetchGasStationsFromAroundApi)
                .collect(Collectors.toMap(
                        OpinetAroundDto::getUniId,
                        station -> station,
                        (existing, replacement) -> existing
                ))
                .flatMapMany(map -> Flux.fromIterable(map.values()))
                .sort(Comparator.comparingInt(OpinetAroundDto::getPrice))
                .take(3)
                .collectList()
                .block();

        if (top3CheapestStations == null || top3CheapestStations.isEmpty()) {
            return Collections.emptyList();
        }

        return Flux.fromIterable(top3CheapestStations)
                .flatMap(this::fetchAndBuildDetailResponse) // 상세 정보 조회 및 최종 DTO 빌드
                .collectList()
                .block();
    }

    private Flux<OpinetAroundDto> fetchGasStationsFromAroundApi(RoutePointRequestDto point) {
        return webClient.get()
                .uri(aroundApiUrl, uriBuilder -> uriBuilder
                        .queryParam("code", apiKey)
                        .queryParam("x", point.getX())
                        .queryParam("y", point.getY())
                        .queryParam("radius", 500)
                        .queryParam("sort", 1) // 가격순 정렬
                        .queryParam("prodcd", TEMP_FUEL_TYPE.getInternalCode())
                        .queryParam("out", "xml")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                // 2. 받은 String을 XmlMapper로 직접 파싱합니다.
                .flatMap(xmlString -> {
                    try {
                        // XmlMapper를 사용해 XML 문자열을 DTO 객체로 변환
                        OpinetXmlResponseDto responseDto = xmlMapper.readValue(xmlString, OpinetXmlResponseDto.class);
                        return Mono.just(responseDto.getOil()); // 주유소 리스트 반환
                    } catch (Exception e) {
                        log.error("fetchGasStationsFromAroundApi - XML 파싱 실패");
                        return Mono.empty();
                    }
                })
                .flatMapMany(Flux::fromIterable)
                .onErrorResume(e -> {
                    log.error("Opinet API 호출 실패: 좌표x={}, 좌표y={} 에러={}", point.getX(), point.getY(), e.getMessage());

                    return Flux.empty();
                });
    }

    private Mono<GasStationResponseDto> fetchAndBuildDetailResponse(OpinetAroundDto simpleStation) {
        return webClient.get()
                .uri(detailApiUrl, uriBuilder -> uriBuilder
                        .queryParam("code", apiKey)
                        .queryParam("id", simpleStation.getUniId())
                        .queryParam("out", "xml")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(xmlString -> {
                    try {
                        OpinetDetailXmlResponse response = xmlMapper.readValue(xmlString, OpinetDetailXmlResponse.class);
                        OpinetDetailOilDto detailInfo = response.getOil();

                        int price = detailInfo.getOilPrice().stream()
                                .filter(oil -> TEMP_FUEL_TYPE.getInternalCode().equals(oil.getProdCd().trim()))
                                .findFirst()
                                .map(OpinetDetailOilPriceDto::getPrice)
                                .orElse(0);

                        if (price == 0) {
                            return Mono.empty();
                        }

                        return Mono.just(GasStationResponseDto.builder()
                                .name(detailInfo.getOsNm())
                                .oldAddress(detailInfo.getVanAdr())
                                .newAddress(detailInfo.getNewAdr())
                                .oilPrice(price)
                                .coordinate(CoordinateDto.builder()
                                        .x(detailInfo.getGisXCoor())
                                        .y(detailInfo.getGisYCoor())
                                        .build())
                                .oil(OilInfoDto.builder()
                                        .price(price)
                                        .fuelType(TEMP_FUEL_TYPE.getCode())
                                        .build())
                                .build());

                    } catch (Exception e) {
                        log.error("fetchAndBuildDetailResponse - XML 파싱 실패 (ID: {})", simpleStation.getUniId());
                        return Mono.empty();
                    }
                })
                .onErrorResume(e -> {
                    log.error("Opinet 상세 정보 API 호출 실패 (ID: {}): {}", simpleStation.getUniId(), e.getMessage());
                    return Mono.empty();
                });
    }
}
