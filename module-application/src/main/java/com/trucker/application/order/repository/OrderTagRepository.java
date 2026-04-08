package com.trucker.application.order.repository;

import com.trucker.application.order.entity.OrderTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderTagRepository extends JpaRepository<OrderTag, Integer> {
    Optional<OrderTag> findByTagName(String tagName);
}
