package com.trucker.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", "Invalid Input Value"),
    INVALID_TYPE_VALUE(400, "INVALID_TYPE_VALUE", "Invalid Type Value"),
    ACCESS_DENIED(403, "ACCESS_DENIED", "Access is Denied"),
    RESOURCE_NOT_FOUND(404, "RESOURCE_NOT_FOUND", "Resource Not Found"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "Internal Server Error"),

    // Auth
    AUTHENTICATION_FAILED(401, "AUTHENTICATION_FAILED", "Authentication Failed"),
    JWT_TOKEN_EXPIRED(401, "JWT_TOKEN_EXPIRED", "JWT Token has Expired"),
    INVALID_REFRESH_TOKEN(401, "INVALID_REFRESH_TOKEN", "Invalid Refresh Token"),
    TOKEN_SIGNATURE_INVALID(401, "TOKEN_SIGNATURE_INVALID", "Invalid JWT Signature"),
    PASSWORD_NOT_MATCH(401, "PASSWORD_NOT_MATCH", "Password Not Matched"),
    USER_NOT_FOUND_FOR_LOGIN(404, "USER_NOT_FOUND_FOR_LOGIN", "User Not Found"),

    // User
    INVALID_USER_STATUS(400, "INVALID_USER_STATUS", "Invalid User Status"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "User Not Found"),
    EMAIL_DUPLICATION(401, "EMAIL_DUPLICATION", "Email Duplicated")

    ;


    private final int status;
    private final String errorCode;
    private final String message;
}
