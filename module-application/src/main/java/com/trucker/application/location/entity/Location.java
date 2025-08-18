package com.trucker.application.location.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "district", nullable = false, length = 50)
    private String district;

    @Column(name = "address_detail", nullable = false)
    private String addressDetail;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longitude", nullable = false)
    private double longitude;

    @Builder
    public Location(String city, String district, String addressDetail, double latitude, double longitude) {
        this.city = city;
        this.district = district;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(String city, String district, String addressDetail,
                       double latitude, double longtitude) {
        this.city = city;
        this.district = district;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longtitude;
    }
}
