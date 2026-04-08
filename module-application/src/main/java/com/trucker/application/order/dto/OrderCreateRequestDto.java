package com.trucker.application.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateRequestDto {
    @NotNull(message = "Pickup location information is required.")
    @Valid
    private LocationDto pickUpLocation;

    @NotNull(message = "Drop-off location information is required.")
    @Valid
    private LocationDto dropOffLocation;

    @NotEmpty(message = "There must be at least one order item.")
    @Valid
    private List<OrderItemDto> orderItems;

    private List<String> orderTags;

    @NotNull(message = "Pickup time is required.")
    @Future(message = "Pickup time must be in the future.")
    private LocalDateTime pickUpDatetime;

    @NotNull(message = "Drop-off time is required.")
    @Future(message = "Drop-off time must be in the future.")
    private LocalDateTime dropOffDatetime;

    @NotNull(message = "Vehicle type is required.")
    private String vehicleType;

    @Min(value = 1, message = "Tonnage must be at least 1.")
    private int tonnage;

    @PositiveOrZero(message = "Payment amount must be a positive number or zero.")
    private int paymentAmount;

    private boolean isPrepaid;

    @Getter
    @NoArgsConstructor
    public static class LocationDto {
        @NotBlank(message = "City is a required field.")
        private String city;

        @NotBlank(message = "District is a required field.")
        private String district;

        @NotBlank(message = "Detailed address is a required field.")
        private String addressDetail;

        @NotNull(message = "Latitude is required.")
        private Double latitude;

        @NotNull(message = "Longitude is required.")
        private Double longitude;
    }

    @Getter
    @NoArgsConstructor
    public static class OrderItemDto {
        @NotBlank(message = "Product name is required.")
        private String productName;

        @Min(value = 1, message = "Quantity must be at least 1.")
        private long quantity;

        private String unit;
    }
}
