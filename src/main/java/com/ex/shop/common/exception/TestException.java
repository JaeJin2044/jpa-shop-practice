package com.ex.shop.common.exception;

import com.ex.shop.common.enums.ResponseCode;

public class TestException extends BusinessException {

  public TestException() {
    super(ResponseCode.TEST_EXCEPTION);
  }

  public TestException(ResponseCode responseCode) {
    super(responseCode);
  }

  public TestException(String message) {
    super(message,ResponseCode.TEST_EXCEPTION);
  }

  public TestException(String message, ResponseCode responseCode) {
    super(message,responseCode);
  }
}
