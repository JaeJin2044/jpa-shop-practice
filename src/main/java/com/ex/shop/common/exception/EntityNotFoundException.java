package com.ex.shop.common.exception;


import com.ex.shop.common.enums.ResponseCode;
import org.apache.commons.lang3.StringUtils;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException() {
        super(ResponseCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }

    public EntityNotFoundException(String message) {
        super(message,ResponseCode.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String message, ResponseCode responseCode) {
        super(message,responseCode);
    }

}
