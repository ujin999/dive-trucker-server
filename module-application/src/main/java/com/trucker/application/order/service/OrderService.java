package com.trucker.application.order.service;

import com.trucker.application.location.dto.CoordinatesDto;
import com.trucker.application.location.entity.DropOffLocation;
import com.trucker.application.location.entity.Location;
import com.trucker.application.location.entity.PickUpLocation;
import com.trucker.application.location.repository.DropOffLocationRepository;
import com.trucker.application.location.repository.LocationRepository;
import com.trucker.application.location.repository.PickUpLocationRepository;
import com.trucker.application.order.dto.*;
import com.trucker.application.order.entity.Order;
import com.trucker.application.order.entity.OrderItem;
import com.trucker.application.order.entity.OrderTag;
import com.trucker.application.order.enums.OrderStatus;
import com.trucker.application.order.repository.OrderRepository;
import com.trucker.application.order.repository.OrderTagRepository;
import com.trucker.application.order.util.KakaoMobilityClient;
import com.trucker.application.order.util.WaitingTimeData;
import com.trucker.application.user.repository.UserRepository;
import com.trucker.common.domain.user.entity.User;
import com.trucker.core.exception.BusinessException;
import com.trucker.core.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final LocationRepository locationRepository;
    private final PickUpLocationRepository pickUpLocationRepository;
    private final DropOffLocationRepository dropOffLocationRepository;
    private final OrderTagRepository orderTagRepository;
    private final UserRepository userRepository;

    private final KakaoMobilityClient kakaoMobilityClient;

    // Note: 추후에 실제 터미널 데이터로 바꿔야 함
    private static final long TERMINAL_CONGESTION_SECONDS = 600;
    private static final long ALPHA_BUFFER_SECONDS = 1800;

    @Transactional
    public OrderDetailResponseDto createOrder(long userId, OrderCreateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Location pickUpLoc = saveLocation(requestDto.getPickUpLocation());
        Location dropOffLoc = saveLocation(requestDto.getDropOffLocation());

        PickUpLocation pickUpLocation = pickUpLocationRepository.save(new PickUpLocation(pickUpLoc));
        DropOffLocation dropOffLocation = dropOffLocationRepository.save(new DropOffLocation(dropOffLoc));

        List<OrderTag> tags = requestDto.getOrderTags().stream()
                .map(tagName -> orderTagRepository.findByTagName(tagName)
                        .orElseGet(() -> orderTagRepository.save(new OrderTag(tagName))))
                .collect(Collectors.toList());

        Order order = Order.builder()
                .user(user)
                .pickUpLocation(pickUpLocation)
                .dropOffLocation(dropOffLocation)
                .pickUpDatetime(requestDto.getPickUpDatetime())
                .dropOffDatetime(requestDto.getDropOffDatetime())
                .vehicleType(requestDto.getVehicleType())
                .tonnage(requestDto.getTonnage())
                .paymentAmount(requestDto.getPaymentAmount())
                .isPrepaid(requestDto.isPrepaid())
                .orderTags(tags)
                .status(OrderStatus.PENDING.getCode())
                .build();

        List<OrderItem> orderItems = requestDto.getOrderItems().stream()
                .map(itemDto -> OrderItem.builder()
                        .order(order)
                        .productName(itemDto.getProductName())
                        .quantity(itemDto.getQuantity())
                        .unit(itemDto.getUnit())
                        .build())
                .collect(Collectors.toList());
        order.setOrderItems(orderItems); // Order 엔티티에 Item 리스트 설정

        Order savedOrder = orderRepository.save(order);

        return buildOrderDetailResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderById(int orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return buildOrderDetailResponse(order);
    }

    private Location saveLocation(OrderCreateRequestDto.LocationDto dto) {
        return locationRepository.save(Location.builder()
                .city(dto.getCity())
                .district(dto.getDistrict())
                .addressDetail(dto.getAddressDetail())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build());
    }

    private OrderDetailResponseDto buildOrderDetailResponse(Order order) {
        Location pickUp = order.getPickUpLocation().getLocation();
        Location dropOff = order.getDropOffLocation().getLocation();
        CoordinatesDto startDto = new CoordinatesDto(pickUp.getLatitude(), pickUp.getLongitude());
        CoordinatesDto endDto = new CoordinatesDto(dropOff.getLatitude(), dropOff.getLongitude());

        return OrderDetailResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getStatus())
                .userId(order.getUser().getId())
                .pickUpLocation(OrderDetailResponseDto.LocationDto.builder()
                        .city(pickUp.getCity())
                        .district(pickUp.getDistrict())
                        .addressDetail(pickUp.getAddressDetail())
                        .build())
                .dropOffLocation(OrderDetailResponseDto.LocationDto.builder()
                        .city(dropOff.getCity())
                        .district(dropOff.getDistrict())
                        .addressDetail(dropOff.getAddressDetail())
                        .build())
                .orderItems(order.getOrderItems().stream()
                        .map(item -> OrderDetailResponseDto.OrderItemDto.builder()
                                .productName(item.getProductName())
                                .quantity(item.getQuantity())
                                .unit(item.getUnit())
                                .build())
                        .collect(Collectors.toList()))
                .orderTags(order.getOrderTags().stream()
                        .map(OrderTag::getTagName)
                        .collect(Collectors.toList()))
                .distance(order.getDistance())
                .pickUpDatetime(order.getPickUpDatetime())
                .dropOffDatetime(order.getDropOffDatetime())
                .actualArrivalDatetime(order.getActualArrivalDatetime())
                .vehicleType(order.getVehicleType())
                .tonnage(order.getTonnage())
                .paymentAmount(order.getPaymentAmount())
                .isPrepaid(order.isPrepaid())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    @Transactional
    public void startOrder(int orderId) {
        Order order = findOrderById(orderId);
        if (order.getStatus() != OrderStatus.PENDING.getCode()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
        order.setStatus(OrderStatus.PROGRESS.getCode());
    }

    @Transactional
    public void completeOrder(int orderId) {
        Order order = findOrderById(orderId);
        if (order.getStatus() != OrderStatus.PROGRESS.getCode()) {
            throw new BusinessException(ErrorCode.INVALID_USER_STATUS);
        }
        order.setStatus(OrderStatus.COMPLETED.getCode());
    }

    @Transactional(readOnly = true)
    public List<OrderDetailResponseDto> getOrdersByUserId(long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::buildOrderDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDetailResponseDto> getCompletedOrdersByUserId(long userId) {
        return orderRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, OrderStatus.COMPLETED.getCode()).stream()
                .map(this::buildOrderDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDetailResponseDto updateOrder(int orderId, OrderUpdateDto updateDto) {
        Order order = findOrderById(orderId);

        if (updateDto.getPickUpDatetime() != null) {
            order.setPickUpDatetime(updateDto.getPickUpDatetime());
        }
        if (updateDto.getDropOffDatetime() != null) {
            order.setDropOffDatetime(updateDto.getDropOffDatetime());
        }

        order.setPrepaid(updateDto.isPrepaid());

        if (updateDto.getPickUpLocation() != null) {
            Location newPickUpLoc = saveLocationFromUpdateDto(updateDto.getPickUpLocation());
            order.setPickUpLocation(new PickUpLocation(newPickUpLoc));
        }
        if (updateDto.getDropOffLocation() != null) {
            Location newDropOffLoc = saveLocationFromUpdateDto(updateDto.getDropOffLocation());
            order.setDropOffLocation(new DropOffLocation(newDropOffLoc));
        }

        if (updateDto.getOrderItems() != null) {
            order.getOrderItems().clear();

            List<OrderItem> newOrderItems = updateDto.getOrderItems().stream()
                    .map(itemDto -> OrderItem.builder()
                            .order(order)
                            .productName(itemDto.getProductName())
                            .quantity(itemDto.getQuantity())
                            .unit(itemDto.getUnit())
                            .build())
                    .collect(Collectors.toList());

            order.getOrderItems().addAll(newOrderItems);
        }

        return buildOrderDetailResponse(order);
    }

    @Transactional
    public void recordActualArrivalTime(int orderId, LocalDateTime actualArrivalDatetime) {
        Order order = findOrderById(orderId);
        order.setActualArrivalDatetime(actualArrivalDatetime);
    }

    @Transactional(readOnly = true)
    public DepartureCalculationResponseDto getOptimalDepartureTime(Integer orderId, DepartureCalculationRequestDto requestDto) {
        CoordinatesDto driverCoords = requestDto.getDriverCoords();
        LocalDateTime requiredArrivalTime = requestDto.getRequiredArrivalTime();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문 정보를 찾을 수 없습니다: " + orderId));

        PickUpLocation pickUpLocation = order.getPickUpLocation();
        Location location = pickUpLocation.getLocation();

        CoordinatesDto pickupCoords = new CoordinatesDto();
        pickupCoords.setLatitude(location.getLatitude());
        pickupCoords.setLongitude(location.getLongitude());

        int initialTravelTime = kakaoMobilityClient.getTravelTime(driverCoords, pickupCoords);

        LocalDateTime predictedDepartureTime = requiredArrivalTime.minusSeconds(initialTravelTime);
        int predictedTravelTime = kakaoMobilityClient.getFutureTravelTime(driverCoords, pickupCoords, predictedDepartureTime);

        long totalOffsetSeconds = predictedTravelTime + WaitingTimeData.getWaitingTime(requiredArrivalTime.getHour()) + ALPHA_BUFFER_SECONDS;

        return new DepartureCalculationResponseDto(requiredArrivalTime.minusSeconds(totalOffsetSeconds));
    }


    // Helper
    private Order findOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
    }

    private Location saveLocationFromUpdateDto(OrderUpdateDto.LocationDto dto) {
        return locationRepository.save(Location.builder()
                .city(dto.getCity())
                .district(dto.getDistrict())
                .addressDetail(dto.getAddressDetail())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build());
    }
}
