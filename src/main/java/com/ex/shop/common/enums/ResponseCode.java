package com.ex.shop.common.enums;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    SUCCESS(HttpStatus.OK,"00","SUCCESS"),
    TEST_EXCEPTION(HttpStatus.BAD_REQUEST,"99","TEST_EXCEPTION"),
    BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST,"90","BUSINESS_EXCEPTION"),
    // Common
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "40", "ENTITY_NOT_FOUND"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "41", "INVALID_INPUT_VALUE"),
    METHOD_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "42", "METHOD_NOT_ALLOWED"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "43", "INVALID_TYPE_VALUE"),
    HANDLE_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "44", "HANDLE_ACCESS_DENIED"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50", "INTERNAL_SERVER_ERROR");

    private final String code;
    private final String message;
    private HttpStatus status;

    ResponseCode(final HttpStatus status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

}