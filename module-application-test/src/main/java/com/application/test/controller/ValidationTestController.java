package com.application.test.controller;

import com.application.test.dto.UserRequestDto;
import com.projectname.core.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationTestController {
    @PostMapping("/api/users")
    public ResponseEntity<ApiResponse<?>> registerUser(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(dto));
    }
}
