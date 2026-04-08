package com.trucker.application.order.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderDetailResponseDto {
    private long orderId;
    private long userId;
    private LocationDto pickUpLocation;
    private LocationDto dropOffLocation;
    private int distance;
    private List<OrderItemDto> orderItems;
    private List<String> orderTags;
    private LocalDateTime pickUpDatetime;
    private LocalDateTime dropOffDatetime;
    private LocalDateTime actualArrivalDatetime;
    private String vehicleType;
    private int tonnage;
    private int paymentAmount;
    private boolean isPrepaid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int orderStatus;

    @Getter
    @Builder
    public static class LocationDto {
        private String city;
        private String district;
        private String addressDetail;
    }

    @Getter
    @Builder
    public static class OrderItemDto {
        private String productName;
        private long quantity;
        private String unit;
    }
}
