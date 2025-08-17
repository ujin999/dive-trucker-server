package com.trucker.application.user.dto;

// used to find user
public record UserResponseDto(
        Long id,
        String username,
        String email) {
}
