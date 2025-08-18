package com.trucker.application.vehicle.repository;

import com.trucker.application.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUserId(Long userId);
    Vehicle findByIdAndUserId(Long id, Long userId);
}
