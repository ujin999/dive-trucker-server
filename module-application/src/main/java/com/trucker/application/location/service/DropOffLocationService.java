package com.trucker.application.location.service;

import com.trucker.application.location.dto.DropOffLocationRequestDto;
import com.trucker.application.location.dto.DropOffLocationResponseDto;
import com.trucker.application.location.dto.LocationResponseDto;
import com.trucker.application.location.entity.DropOffLocation;
import com.trucker.application.location.entity.Location;
import com.trucker.application.location.repository.DropOffLocationRepository;
import com.trucker.application.location.repository.LocationRepository;
import com.trucker.core.exception.BusinessException;
import com.trucker.core.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DropOffLocationService {
    private final DropOffLocationRepository dropOffLocationRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public DropOffLocationResponseDto registerDropOffLocation(DropOffLocationRequestDto requestDto) {
        Location location = locationRepository.findById(requestDto.getLocationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        DropOffLocation dropOffLocation = DropOffLocation.builder()
                .location(location)
                .build();

        DropOffLocation savedDropOffLocation = dropOffLocationRepository.save(dropOffLocation);

        return convertToResponseDto(savedDropOffLocation);
    }

    public DropOffLocationResponseDto findById(Long id) {
        DropOffLocation dropOffLocation = dropOffLocationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        return convertToResponseDto(dropOffLocation);
    }

    private DropOffLocationResponseDto convertToResponseDto(DropOffLocation dropOffLocation) {
        return DropOffLocationResponseDto.builder()
                .id(dropOffLocation.getId())
                .location(
                        LocationResponseDto.builder()
                                .id(dropOffLocation.getLocation().getId())
                                .city(dropOffLocation.getLocation().getCity())
                                .district(dropOffLocation.getLocation().getDistrict())
                                .addressDetail(dropOffLocation.getLocation().getAddressDetail())
                                .latitude(dropOffLocation.getLocation().getLatitude())
                                .longitude(dropOffLocation.getLocation().getLongitude())
                                .build()
                )
                .build();
    }
}
