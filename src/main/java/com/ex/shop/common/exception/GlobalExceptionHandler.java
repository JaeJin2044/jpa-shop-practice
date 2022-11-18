package com.ex.shop.common.exception;

import com.ex.shop.common.enums.ResponseCode;
import com.ex.shop.common.model.ErrorResponse;
import java.nio.file.AccessDeniedException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice(annotations = RestController.class)
@Slf4j
public class GlobalExceptionHandler {

  /**
   * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
   * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
   * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
    MethodArgumentNotValidException e, HttpServletRequest req) {

    log.info("handleMethodArgumentNotValidException / URI : {} ", req.getRequestURI(), e);
    final ErrorResponse response = ErrorResponse.of(ResponseCode.INVALID_INPUT_VALUE, e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * @ModelAttribute 으로 binding error 발생시 BindException 발생한다.
   * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
   */
  @ExceptionHandler(BindException.class)
  protected ResponseEntity<ErrorResponse> handleBindException(
    BindException e, HttpServletRequest req) {

    log.info("handleBindException / URI : {} ",req.getRequestURI() , e);
    final ErrorResponse response = ErrorResponse.of(ResponseCode.INVALID_INPUT_VALUE, e.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * enum type 일치하지 않아 binding 못할 경우 발생
   * 주로 @RequestParam enum으로 binding 못했을 경우 발생
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
    MethodArgumentTypeMismatchException e, HttpServletRequest req) {

    log.info("handleMethodArgumentTypeMismatchException / URI : {}", req.getRequestURI() , e);
    final ErrorResponse response = ErrorResponse.of(e);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * 지원하지 않은 HTTP method 호출 할 경우 발생
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
    HttpRequestMethodNotSupportedException e, HttpServletRequest req) {

    log.info("handleHttpRequestMethodNotSupportedException / URI : {}", req.getRequestURI(), e);
    final ErrorResponse response = ErrorResponse.of(ResponseCode.METHOD_NOT_ALLOWED);
    return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
  }

  /**
   * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
   */
  @ExceptionHandler(AccessDeniedException.class)
  protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest req) {

    log.info("handleAccessDeniedException / URI : {}", req.getRequestURI() , e);
    final ErrorResponse response = ErrorResponse.of(ResponseCode.HANDLE_ACCESS_DENIED);
    return new ResponseEntity<>(response, ResponseCode.HANDLE_ACCESS_DENIED.getStatus());
  }

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e, HttpServletRequest req) {

    log.info("BusinessException / URI : {}", req.getRequestURI() , e);
    final ResponseCode responseCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(responseCode, e.getMessage());
    return new ResponseEntity<>(response, responseCode.getStatus());
  }


  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest req) {
    log.info("Exception / URI : {} ", req.getRequestURI(), e);
    final ErrorResponse response = ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR);
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(TestException.class)
  protected ResponseEntity<ErrorResponse> handleTestException(final TestException e, HttpServletRequest req) {

    log.info("TestException / URI : {} ", req.getRequestURI(), e);
    final ResponseCode responseCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(responseCode, e.getMessage());
    return new ResponseEntity<>(response, responseCode.getStatus());
  }
}
