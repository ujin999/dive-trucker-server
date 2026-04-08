package com.trucker.application.order.repository;

import com.trucker.application.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUserIdOrderByCreatedAtDesc(long userId);

    List<Order> findByUserIdAndStatusOrderByCreatedAtDesc(long userId, int status);
}
