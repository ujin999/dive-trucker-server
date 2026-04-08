package com.trucker.application.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class OrderArrivalUpdateRequestDto {
    @NotNull(message = "Actual arrival time is required.")
    private LocalDateTime actualArrivalDatetime;
}
