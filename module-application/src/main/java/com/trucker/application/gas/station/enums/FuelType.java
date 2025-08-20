package com.trucker.application.gas.station.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FuelType {
    GASOLINE(0, "B027"),
    DIESEL(1, "D047"),
    PREMIUM_GASOLINE(2, "B034");

    private final int code;
    private final String internalCode;

    public static FuelType ofCode(int code) {
        return Arrays.stream(FuelType.values())
                .filter(fuelType -> fuelType.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid CarType code: " + code));
    }
}
