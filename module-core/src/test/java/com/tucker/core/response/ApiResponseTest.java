package com.tucker.core.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Api Response Tests")
public class ApiResponseTest {
    @Test
    @DisplayName("Successfully creates a success response DTO")
    void testSuccessResponse() {
        // given
        String testData = "Welcome";

        // when
        ApiResponse<String> response = ApiResponse.success(testData);

        // then
        assertThat(response.status()).isEqualTo(200);
        assertThat(response.message()).isEqualTo("Success");
        assertThat(response.data()).isEqualTo(testData);
    }

    @Test
    @DisplayName("Successfully creates a success response DTO witout data")
    void testSuccessResponseWithoutData() {
        // Given

        // When
        ApiResponse<Void> response = ApiResponse.success();

        // Then
        assertThat(response.status()).isEqualTo(200);
        assertThat(response.message()).isEqualTo("Success");
        assertThat(response.data()).isNull();
    }
}
