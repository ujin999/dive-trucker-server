package com.trucker.application.busan_new_port_geography.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NavigationStep {
    private int instruction; // 0(좌회전), 1(직진), 2(우회전)
    private String coordinate;
}
