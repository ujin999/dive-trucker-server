package com.trucker.application.order.entity;

import com.trucker.application.location.entity.DropOffLocation;
import com.trucker.application.location.entity.PickUpLocation;
import com.trucker.common.domain.user.entity.User;
import com.trucker.core.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pick_up_location_id", nullable = false)
    private PickUpLocation pickUpLocation;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "drop_off_location_id", nullable = false)
    private DropOffLocation dropOffLocation;

    @ManyToMany
    @JoinTable(
            name = "order_has_tags", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<OrderTag> orderTags = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "distance")
    private int distance;

    @Column(name = "pick_up_datetime", nullable = false)
    private LocalDateTime pickUpDatetime;

    @Column(name = "drop_off_datetime", nullable = false)
    private LocalDateTime dropOffDatetime;

    @Column(name = "actual_arrival_datetime")
    private LocalDateTime actualArrivalDatetime;

    @Column(name = "vehicle_type", nullable = false)
    private String vehicleType;

    @Column(name = "tonnage", nullable = false)
    private Integer tonnage;

    @Column(name = "payment_amount", nullable = false)
    private Integer paymentAmount;

    @Column(name = "payment_type", nullable = false)
    private boolean isPrepaid;

    @Column(name = "status", nullable = false)
    private int status;
}
