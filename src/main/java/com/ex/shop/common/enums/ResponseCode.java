package com.ex.shop.common.enums;

import org.springframework.http.HttpStatus;

public enum ResponseCode {
    SUCCESS(HttpStatus.OK,"00","SUCCESS"),

    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "100", "ENTITY_NOT_FOUND"),
    OUT_OF_STOCK(HttpStatus.BAD_REQUEST,"44","OUT_OF_STOCK"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "40", "INVALID_INPUT_VALUE"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "41", "INVALID_TYPE_VALUE"),
    METHOD_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "42", "METHOD_NOT_ALLOWED"),
    HANDLE_ACCESS_DENIED(HttpStatus.BAD_REQUEST, "43", "HANDLE_ACCESS_DENIED"),
    BUSINESS_EXCEPTION(HttpStatus.BAD_REQUEST, "44","BUSINESS_EXCEPTION"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50", "INTERNAL_SERVER_ERROR"),

    // Common
    TEST_EXCEPTION(HttpStatus.BAD_REQUEST,"1000","TEST_EXCEPTION");


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