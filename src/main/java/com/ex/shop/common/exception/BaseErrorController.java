package com.ex.shop.common.exception;

import com.ex.shop.common.enums.ResponseCode;
import com.ex.shop.common.model.ErrorResponse;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseErrorController implements ErrorController {

  /**
   * 서블릿단의 에러 Request ERROR
   * @param response
   * @return
   */
  @RequestMapping(path = "/error", produces = MediaType.TEXT_HTML_VALUE)
  public ModelAndView errorHtml(HttpServletResponse response) {
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
        "errorCode", responseCode.getCode(),
        "message", responseCode.getMessage()
      ),
      httpStatus
    );
  }

  /**
   * 서블릿단의 에러 Request ERROR(API용)
   * @param response
   * @return
   */
  @RequestMapping("/error")
  public ResponseEntity<ErrorResponse> error(HttpServletResponse response) {
    HttpStatus httpStatus = HttpStatus.valueOf(response.getStatus());
    ResponseCode responseCode = httpStatus.is4xxClientError() ? ResponseCode.INVALID_INPUT_VALUE
      : ResponseCode.INTERNAL_SERVER_ERROR;

    if (httpStatus == HttpStatus.OK) {
      httpStatus = HttpStatus.FORBIDDEN;
      responseCode = ResponseCode.INVALID_INPUT_VALUE;
    }
    return new ResponseEntity<>(ErrorResponse.of(responseCode), httpStatus);
  }
}