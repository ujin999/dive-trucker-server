package com.trucker.application.location.service;

import com.trucker.application.location.dto.LocationRequestDto;
import com.trucker.application.location.dto.LocationResponseDto;
import com.trucker.application.location.entity.Location;
import com.trucker.application.location.repository.LocationRepository;
import com.trucker.core.exception.ErrorCode;
import com.trucker.core.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<LocationResponseDto> findAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public LocationResponseDto findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));
        return convertToResponseDto(location);
    }

    @Transactional
    public LocationResponseDto registerLocation(LocationRequestDto requestDto) {
        Location location = Location.builder()
                .city(requestDto.getCity())
                .district(requestDto.getDistrict())
                .addressDetail(requestDto.getAddressDetail())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .build();

        Location savedLocation = locationRepository.save(location);

        return convertToResponseDto(savedLocation);
    }

    @Transactional
    public LocationResponseDto updateLocation(Long id, LocationRequestDto requestDto) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        location.update(requestDto.getCity(), requestDto.getDistrict(), requestDto.getAddressDetail(),
                requestDto.getLatitude(), requestDto.getLongitude());

        return convertToResponseDto(location);
    }

    @Transactional
    public void deleteLocation(Long id) {
        locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.RESOURCE_NOT_FOUND));

        locationRepository.deleteById(id);
    }

    private LocationResponseDto convertToResponseDto(Location location) {
        return LocationResponseDto.builder()
                .id(location.getId())
                .city(location.getCity())
                .district(location.getDistrict())
                .addressDetail(location.getAddressDetail())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .build();
    }
}
