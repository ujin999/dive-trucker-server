package com.trucker.application.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderUpdateDto {
    @Valid
    private LocationDto pickUpLocation;

    @Valid
    private LocationDto dropOffLocation;

    @Valid
    private List<OrderCreateRequestDto.OrderItemDto> orderItems;

    @Future(message = "Pickup time must be in the future.")
    private LocalDateTime pickUpDatetime;

    @Future(message = "Drop-off time must be in the future.")
    private LocalDateTime dropOffDatetime;

    private boolean isPrepaid;
    @Getter
    @NoArgsConstructor
    public static class LocationDto {
        private String city;
        private String district;
        private String addressDetail;
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @NoArgsConstructor
    public static class OrderItemDto {
        private String productName;

        @Min(value = 1, message = "Quantity must be at least 1.")
        private long quantity;

        private String unit;
    }
}
