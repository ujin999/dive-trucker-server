package com.application.test.controller;

import com.application.test.dto.UserRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectname.core.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ValidationTestController.class)
@DisplayName("Validation controller test")
class ValidationTestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Name, Email, Password are valid")
    void testRequiredInputError() throws Exception {
        // Given
        UserRequestDto dto = new UserRequestDto("", "", "");

        String json  = objectMapper.writeValueAsString(dto);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(ErrorCode.INVALID_INPUT_VALUE.getStatus()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(4))
                .andExpect(jsonPath("$.errors[0].field").exists())
                .andExpect(jsonPath("$.errors[0].defaultMessage").exists());

    }

    @Test
    @DisplayName("The request with valid user information will succeed.")
    void testRegisterUserSuccess() throws Exception {
        // Given
        UserRequestDto dto = new UserRequestDto(
                "test",
                "test@example.com",
                "password12!");

        String json = objectMapper.writeValueAsString(dto);

        // When
        ResultActions resultActions = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }
}