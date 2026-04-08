package com.trucker.application.busan_new_port_geography.repository;

import com.trucker.application.busan_new_port_geography.entity.PnitLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PnitLineRepository extends JpaRepository<PnitLine, Integer> {
    PnitLine findByRoadName(String roadName);

    @Query("SELECT DISTINCT l FROM PnitLine l JOIN FETCH l.vertices WHERE l.roadName LIKE :prefix%")
    List<PnitLine> findPnitLinesWithVerticesByRoadNameStartingWith(@Param("prefix") String prefix);
    List<PnitLine> findPnitLinesByRoadNameStartingWith(String str);
}
