package com.trucker.application.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDto {
    private Long id;
    private String city;
    private String district;
    private String addressDetail;
    private Double latitude;
    private Double longitude;
}
