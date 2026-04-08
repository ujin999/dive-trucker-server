package com.trucker.application.order.util;

import com.trucker.application.location.dto.CoordinatesDto;
import com.trucker.application.order.dto.kakao.KakaoApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class KakaoMobilityClient {
    private final RestTemplate restTemplate;
    private final String kakaoRestApiKey;
    private final String kakaoDirectionsUrl;
    private final String kakaoFutureDirectionsUrl;

    public KakaoMobilityClient(RestTemplateBuilder builder,
                               @Value("${kakao.api.rest-key}") String kakaoRestApiKey,
                               @Value("${kakao.api.directions-url}") String kakaoDirectionsUrl,
                               @Value("${kakao.api.future-directions-url}") String kakaoFutureDirectionsUrl) {
        this.restTemplate = builder.build();
        this.kakaoRestApiKey = kakaoRestApiKey;
        this.kakaoDirectionsUrl = kakaoDirectionsUrl;
        this.kakaoFutureDirectionsUrl = kakaoFutureDirectionsUrl;
    }

    public int getTravelTime(CoordinatesDto origin, CoordinatesDto destination) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoDirectionsUrl)
                .queryParam("origin", origin.getLongitude() + "," + origin.getLatitude())
                .queryParam("destination", destination.getLongitude() + "," + destination.getLatitude())
                .queryParam("priority", "RECOMMEND")
                .queryParam("car_type", 4)
                .queryParam("car_width", 249)
                .queryParam("car_height", 333)
                .queryParam("car_weight", 20)
                .queryParam("summary", true);

        System.out.println(uriBuilder.toUriString());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoApiResponseDto> response = restTemplate.exchange(
                uriBuilder.toUriString(), HttpMethod.GET, entity, KakaoApiResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getRoutes().length > 0) {
            return response.getBody().getRoutes()[0].getSummary().getDuration();
        }

        throw new RuntimeException("카카오 API 호출에 실패했습니다. Status: " + response.getStatusCode());
    }

    public int getFutureTravelTime(CoordinatesDto origin, CoordinatesDto destination, LocalDateTime departureTime) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoFutureDirectionsUrl) // URL 변경
                .queryParam("origin", origin.getLongitude() + "," + origin.getLatitude())
                .queryParam("destination", destination.getLongitude() + "," + destination.getLatitude())
                .queryParam("departure_time", departureTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")))
                .queryParam("prediction_type", "TRAFFIC_AND_DEMAND") // 필수 파라미터 추가
                .queryParam("priority", "RECOMMEND")
                .queryParam("car_type", 4)
                .queryParam("car_width", 249)
                .queryParam("car_height", 333)
                .queryParam("car_weight", 20)
                .queryParam("summary", true);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoApiResponseDto> response = restTemplate.exchange(
                uriBuilder.toUriString(), HttpMethod.GET, entity, KakaoApiResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody().getRoutes().length > 0) {
            return response.getBody().getRoutes()[0].getSummary().getDuration();
        }

        throw new RuntimeException("카카오 미래 운행 정보 API 호출에 실패했습니다. Status: " + response.getStatusCode());
    }

    public int getTravelDistance(CoordinatesDto origin, CoordinatesDto destination) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoDirectionsUrl)
                .queryParam("origin", origin.getLongitude() + "," + origin.getLatitude())
                .queryParam("destination", destination.getLongitude() + "," + destination.getLatitude())
                .queryParam("priority", "RECOMMEND")
                .queryParam("car_type", 4)
                .queryParam("car_width", 249)
                .queryParam("car_height", 333)
                .queryParam("car_weight", 20)
                .queryParam("summary", true);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KakaoApiResponseDto> response = restTemplate.exchange(
                uriBuilder.toUriString(), HttpMethod.GET, entity, KakaoApiResponseDto.class);

        if (response.getStatusCode().is2xxSuccessful()
                && response.getBody() != null
                && response.getBody().getRoutes().length > 0) {
            return response.getBody().getRoutes()[0].getSummary().getDistance();
        }

        throw new RuntimeException("카카오 API 호출에 실패했습니다. Status: " + response.getStatusCode());
    }
}
