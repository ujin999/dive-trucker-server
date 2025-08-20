package com.trucker.application.location.repository;

import com.trucker.application.location.entity.PickUpLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickUpLocationRepository extends JpaRepository<PickUpLocation, Long> {
}
