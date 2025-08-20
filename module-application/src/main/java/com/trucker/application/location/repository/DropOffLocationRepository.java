package com.trucker.application.location.repository;

import com.trucker.application.location.entity.DropOffLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DropOffLocationRepository extends JpaRepository<DropOffLocation, Long> {

}
