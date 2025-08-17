package com.trucker.application.user.service;

import com.trucker.application.user.dto.UserCreateDto;
import com.trucker.application.user.dto.UserResponseDto;
import com.trucker.application.user.repository.UserRepository;
import com.trucker.common.domain.user.entity.User;
import com.trucker.core.exception.BusinessException;
import com.trucker.core.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserCreateDto createDto) {
        if (userRepository.existsByEmail(createDto.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATION);
        }

        User user = User.builder()
                .username(createDto.getUsername())
                .email(createDto.getEmail())
                .phoneNumber(createDto.getPhoneNumber())
                .build();

        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
