package com.trucker.application.vehicle.entity;

import com.trucker.common.domain.user.entity.User;
import com.trucker.core.config.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "vehicles")
public class Vehicle extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "car_type", nullable = false)
    private Integer carType;

    @Column(name = "license_plate", nullable = false)
    private String licensePlate;

    @Column(name = "has_temperature_control", nullable = false)
    private Boolean hasTemperatureControl;

    @Column(name = "cargobin_length", nullable = false)
    private String cargobinLength;

    @Column(name = "cargobin_width")
    private String cargobinWidth;

    @Column(name = "fuel_type", nullable = false)
    private Integer fuelType;

    @Builder
    public Vehicle(User user, Integer carType, String licensePlate, Boolean hasTemperatureControl, String cargobinLength, String cargobinWidth, Integer fuelType) {
        this.user = user;
        this.carType = carType;
        this.licensePlate = licensePlate;
        this.hasTemperatureControl = hasTemperatureControl;
        this.cargobinLength = cargobinLength;
        this.cargobinWidth = cargobinWidth;
        this.fuelType = fuelType;
    }

    public void update(User user, Integer carType, String licensePlate,
                       Boolean hasTemperatureControl, String cargobinLength,
                       String cargobinWidth, Integer fuelType) {
        this.user = user;
        this.carType = carType;
        this.licensePlate = licensePlate;
        this.hasTemperatureControl = hasTemperatureControl;
        this.cargobinLength = cargobinLength;
        this.cargobinWidth = cargobinWidth;
        this.fuelType = fuelType;
    }

    public void updateCarType(Integer carType) {
        this.carType = carType;
    }

    public void updateLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void updateHasTemperatureControl(Boolean hasTemperatureControl) {
        this.hasTemperatureControl = hasTemperatureControl;
    }

    public void updateCargobinLength(String cargobinLength) {
        this.cargobinLength = cargobinLength;
    }

    public void updateCargobinWidth(String cargobinWidth) {
        this.cargobinWidth = cargobinWidth;
    }

    public void updateFuelType(Integer fuelType) {
        this.fuelType = fuelType;
    }
}
