package com.trucker.application.location.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesDto {
    private Double latitude;
    private Double longitude;
}
