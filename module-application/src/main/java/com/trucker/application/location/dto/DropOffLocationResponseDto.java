package com.trucker.application.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropOffLocationResponseDto {
    private Long id;
    private LocationResponseDto location;
}
