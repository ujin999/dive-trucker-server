package com.trucker.application.busan_new_port_geography.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NavigationStepDto {
    private int instruction;
    private CoordinateDto coordinate;
}
