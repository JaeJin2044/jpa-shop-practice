package com.ex.shop.common.exception;

import com.ex.shop.common.enums.ResponseCode;

public class OutOfStockException extends BusinessException {

  public OutOfStockException() {
    super(ResponseCode.OUT_OF_STOCK);
  }

  public OutOfStockException(ResponseCode responseCode) {
    super(responseCode);
  }

  public OutOfStockException(String message) {
    super(message,ResponseCode.OUT_OF_STOCK);
  }

  public OutOfStockException(String message, ResponseCode responseCode) {
    super(message,responseCode);
  }
}
