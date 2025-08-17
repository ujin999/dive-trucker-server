package com.trucker.application.user.controller;

import com.trucker.application.user.dto.UserCreateDto;
import com.trucker.application.user.dto.UserResponseDto;
import com.trucker.application.user.service.UserService;
import com.trucker.core.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(
            @Valid @RequestBody UserCreateDto userCreateDto) {
        UserResponseDto createdUser = userService.createUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(createdUser));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findAllUser() {
        List<UserResponseDto> users = userService.findAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> findUserById(@PathVariable Long id) {
        UserResponseDto user = userService.findUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
