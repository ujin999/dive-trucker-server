package com.trucker.application.order.dto.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoApiResponseDto {
    private Route[] routes;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Route {
        private Summary summary;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {
        private int duration; // 소요 시간 (초)
        private int distance;
    }
}
