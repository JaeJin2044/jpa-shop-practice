package com.ex.shop.common.exception;

import com.ex.shop.common.enums.ResponseCode;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler
  public ModelAndView exception(Exception e, HttpServletResponse response) {
    HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
    ResponseCode responseCode = httpStatus.is4xxClientError() ? ResponseCode.INVALID_INPUT_VALUE
      : ResponseCode.INTERNAL_SERVER_ERROR;

    if (httpStatus == HttpStatus.OK) {
      httpStatus = HttpStatus.FORBIDDEN;
      responseCode = ResponseCode.INVALID_INPUT_VALUE;
    }

    return new ModelAndView(
      "/error",
      Map.of(
        "statusCode", httpStatus.value(),
        "errorCode", responseCode,
        "message", responseCode.getMessage()
      ),
      httpStatus
    );
  }
}
