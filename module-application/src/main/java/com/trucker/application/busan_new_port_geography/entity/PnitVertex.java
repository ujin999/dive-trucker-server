package com.trucker.application.busan_new_port_geography.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sound.sampled.Line;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pnit_vertices")
public class PnitVertex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vertex_id")
    private Integer vertexId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private PnitLine line;

    @Column(name = "\"order\"")
    private int order;

    private double longitude;
    private double latitude;
}
