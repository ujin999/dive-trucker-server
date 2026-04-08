package com.trucker.application.order.controller;

import com.trucker.application.order.dto.*;
import com.trucker.application.order.service.OrderService;
import com.trucker.core.response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.hibernate.validator.internal.engine.messageinterpolation.el.RootResolver.FORMATTER;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    // NOTE: 인증/인가 구현 후 수정
    static final long TEMP_USER_ID = 6;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDetailResponseDto>> createOrder(
            @Valid @RequestBody OrderCreateRequestDto requestDto) {

        OrderDetailResponseDto createdOrder = orderService.createOrder(TEMP_USER_ID, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdOrder));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDetailResponseDto>>> getOrdersByUser() {
        List<OrderDetailResponseDto> orders = orderService.getOrdersByUserId(TEMP_USER_ID);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponseDto>> getOrderDetails(
            @PathVariable int orderId) {

        OrderDetailResponseDto orderDetails = orderService.getOrderById(orderId);
        return ResponseEntity.ok(ApiResponse.success(orderDetails));
    }

    @PatchMapping("/{orderId}/start")
    public ResponseEntity<OrderDetailResponseDto> startOrder(@PathVariable int orderId) {
        orderService.startOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{orderId}/complete")
    public ResponseEntity<OrderDetailResponseDto> completeOrder(@PathVariable int orderId) {
        orderService.completeOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public ResponseEntity<ApiResponse<List<OrderDetailResponseDto>>> getCompletedOrdersByUser() {
        List<OrderDetailResponseDto> orders = orderService.getCompletedOrdersByUserId(TEMP_USER_ID);
        return ResponseEntity.ok(ApiResponse.success(orders));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderDetailResponseDto>> updateOrder(
            @PathVariable int orderId,
            @RequestBody OrderUpdateDto updateDto) {

        OrderDetailResponseDto updatedOrder = orderService.updateOrder(orderId, updateDto);
        return ResponseEntity.ok(ApiResponse.success(updatedOrder));
    }

    @PatchMapping("/{orderId}/arrival")
    public ResponseEntity<OrderDetailResponseDto> recordArrivalTime(
            @PathVariable int orderId,
            @Valid @RequestBody OrderArrivalUpdateRequestDto requestDto) {

        orderService.recordActualArrivalTime(
                orderId,
                requestDto.getActualArrivalDatetime()
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/calculate-departure")
    public ResponseEntity<ApiResponse<DepartureCalculationResponseDto>> calculateOptimalDepartureTime(
            @PathVariable Integer orderId,
            @RequestBody DepartureCalculationRequestDto request) {

        DepartureCalculationResponseDto optimalDepartureTime = orderService.getOptimalDepartureTime(
                orderId,
                request
        );

        return ResponseEntity.ok(ApiResponse.success(optimalDepartureTime));
    }
}
