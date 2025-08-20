package com.trucker.application.location.service;

import com.trucker.application.location.dto.LocationResponseDto;
import com.trucker.application.location.dto.PickUpLocationRequestDto;
import com.trucker.application.location.dto.PickUpLocationResponseDto;
import com.trucker.application.location.entity.Location;
import com.trucker.application.location.entity.PickUpLocation;
import com.trucker.application.location.repository.LocationRepository;
import com.trucker.application.location.repository.PickUpLocationRepository;
import com.trucker.core.exception.BusinessException;
import com.trucker.core.exception.ErrorCode;
import com.trucker.core.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickUpLocationService {
    private final PickUpLocationRepository pickUpLocationRepository;
    private final LocationRepository locationRepository;

    @Transactional
    public PickUpLocationResponseDto registerPickUpLocation(PickUpLocationRequestDto requestDto) {
        Location location = locationRepository.findById(requestDto.getLocationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

        PickUpLocation pickUpLocation = PickUpLocation.builder()
                .location(location)
                .build();

        PickUpLocation savedPickUpLocation = pickUpLocationRepository.save(pickUpLocation);

        return convertToResponseDto(savedPickUpLocation);
    }

    public PickUpLocationResponseDto findById(Long id) {
        PickUpLocation pickUpLocation = pickUpLocationRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        return convertToResponseDto(pickUpLocation);
    }

    private PickUpLocationResponseDto convertToResponseDto(PickUpLocation pickUpLocation) {
        return PickUpLocationResponseDto.builder()
                .id(pickUpLocation.getId())
                .location(
                        LocationResponseDto.builder()
                                .id(pickUpLocation.getLocation().getId())
                                .city(pickUpLocation.getLocation().getCity())
                                .district(pickUpLocation.getLocation().getDistrict())
                                .addressDetail(pickUpLocation.getLocation().getAddressDetail())
                                .latitude(pickUpLocation.getLocation().getLatitude())
                                .longitude(pickUpLocation.getLocation().getLongitude())
                                .build()
                )
                .build();
    }
}
