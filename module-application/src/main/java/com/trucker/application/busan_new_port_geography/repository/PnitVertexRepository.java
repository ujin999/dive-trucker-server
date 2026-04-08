package com.trucker.application.busan_new_port_geography.repository;

import com.trucker.application.busan_new_port_geography.entity.PnitLine;
import com.trucker.application.busan_new_port_geography.entity.PnitVertex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PnitVertexRepository extends JpaRepository<PnitVertex, Integer> {
    PnitVertex findTopByLineOrderByOrderAsc(PnitLine line);
    PnitVertex findTopByLineOrderByOrderDesc(PnitLine line);
}
