package com.trucker.application.order.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_tags")
@Getter
@NoArgsConstructor
public class OrderTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tag_name", nullable = false, length = 50, unique = true)
    private String tagName;

    public OrderTag(String tagName) {
        this.tagName = tagName;
    }
}
