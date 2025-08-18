package com.trucker.application.vehicle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FuelType {
    GASOLINE(0, "KNCarFuel_Gasoline", "휘발유(가솔린)"),
    PREMIUM_GASOLINE(1, "KNCarFuel_Premium_Gasoline", "고급 휘발유"),
    DIESEL(2, "KNCarFuel_Diesel", "경유(디젤)"),
    LPG(3, "KNCarFuel_LPG", "LPG (액화 석유 가스)"),
    ELECTRIC(4, "KNCarFuel_Electric", "전기"),
    HYBRID_ELECTRIC(5, "KNCarFuel_HybridElectric", "하이브리드 전기"),
    PLUGIN_HYBRID_ELECTRIC(6, "KNCarFuel_PlugInHybridElectric", "플러그인(Plug-in) 하이브리드 전기"),
    HYDROGEN(7, "KNCarFuel_Hydrogen", "수소");

    private final int code;
    private final String internalName;
    private final String description;

    public static FuelType ofCode(int code) {
        return Arrays.stream(FuelType.values())
                .filter(fuelType -> fuelType.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid FuelType code: " + code));
    }
}
