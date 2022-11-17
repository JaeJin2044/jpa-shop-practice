package com.ex.shop.common.exception;

import com.ex.shop.common.enums.ResponseCode;
import org.apache.commons.lang3.StringUtils;

public class BusinessException extends RuntimeException {

    private ResponseCode responseCode;
    private String message;

    public BusinessException(String message, ResponseCode responseCode) {
        super(message);
        this.message = message;
        this.responseCode = responseCode;
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
        this.responseCode = ResponseCode.BUSINESS_EXCEPTION;
    }

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getErrorCode() {
        return responseCode;
    }

    public String getMessage(){
        return message;
    }
}
