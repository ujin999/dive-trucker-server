package com.trucker.core.response;

import java.util.List;

public record ApiErrorResponse(
        int status,
        String errorCode,
        String message,
        List<ValidationError> errors) {
    public static ApiErrorResponse of(int status, String errorCode, String message) {
        return new ApiErrorResponse(status, errorCode, message, null);
    }

    public static ApiErrorResponse of(int status, String errorCode, String message, List<ValidationError> errors) {
        return new ApiErrorResponse(status, errorCode, message, errors);
    }

    public record ValidationError(String field, String defaultMessage) {}
}
