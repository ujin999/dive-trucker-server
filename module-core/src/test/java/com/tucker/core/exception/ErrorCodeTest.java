package com.tucker.core.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Error Code Tests")
class ErrorCodeTest {
    @Test
    @DisplayName("Enum value of INVALID_INPUT_VALUE has a correct value")
    void testInvalidInputTest() {
        // Given
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;

        // When & Then
        assertThat(errorCode.getStatus()).isEqualTo(400);
        assertThat(errorCode.getErrorCode()).isEqualTo("INVALID_INPUT_VALUE");
        assertThat(errorCode.getMessage()).isEqualTo("Invalid Input Value");
    }
}