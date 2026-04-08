package com.trucker.application.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PENDING(0),     // 대기 중
    PROGRESS(1),    // 진행 중
    COMPLETED(2),   // 완료
    CANCELED(3);    // 취소

    private final int code;

    private static OrderStatus ofCode(int code) {
        return Arrays.stream(OrderStatus.values())
                .filter(orderStatus -> orderStatus.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid OrderStatus Code" + code));
    }
}
