package com.trucker.application.busan_new_port_geography.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "pnit_lines")
public class PnitLine {
    @Id
    @Column(name = "line_id")
    private int id;

    @Column(name = "road_name")
    private String roadName;

    private Double distance;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order ASC")
    private Set<PnitVertex> vertices = new LinkedHashSet<>();

    public void addVertex(PnitVertex vertex) {
        this.vertices.add(vertex);
        vertex.setLine(this);
    }
}
