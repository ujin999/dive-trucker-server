package com.trucker.application.vehicle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum CarType {
    TYPE_1(0, "KNCarType_1", "소형차 (예: 승용차, 16인승 이하 승합차, 2.5톤 미만 화물차)", "2축 차량, 윤폭 279.4mm 이하"),
    TYPE_2(1, "KNCarType_2", "중형차 (예: 승합차 17-32인승, 2.5~5.5톤 화물차)", "2축 차량, 윤폭 279.4mm 초과, 윤거 1,800mm 이하"),
    TYPE_3(2, "KNCarType_3", "대형차 (예: 승합차 33인승 이상, 5.5~10톤 화물차)", "2축 차량, 윤폭 279.4mm 초과, 윤거 1,800mm 초과"),
    TYPE_4(3, "KNCarType_4", "대형 화물차 (예: 10~20톤 화물차)", "3축 차량"),
    TYPE_5(4, "KNCarType_5", "특수 화물차 (예: 20톤 이상 화물차)", "4축 이상 차량"),
    TYPE_6(5, "KNCarType_6", "경차", "배기량이 1,000 cc 미만으로 길이 3.6m, 너비 1.6m, 높이 2.0m 이하인 차량"),
    TYPE_BIKE(6, "KNCarType_Bike", "이륜차", "해당 없음");


    private final int code;
    private final String internalName;
    private final String description;
    private final String criteria;

    public static CarType ofCode(int code) {
        return Arrays.stream(CarType.values())
                .filter(carType -> carType.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid CarType code: " + code));
    }
}
