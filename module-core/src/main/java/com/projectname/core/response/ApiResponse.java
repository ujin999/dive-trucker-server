package com.projectname.core.response;

public record ApiResponse<T>(
        int status,
        String message,
        T data) {
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, "Success", null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }
}
