package com.ex.shop.common.model;

import com.ex.shop.common.enums.ResponseCode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
@NoArgsConstructor
public class ErrorResponse {

  private String message;
  private int status;
  private List<FieldError> errors;
  private String code;

  public ErrorResponse(final ResponseCode code, final List<FieldError> errors) {
    this.message = code.getMessage();
    this.status = code.getStatus().value();
    this.errors = errors;
    this.code = code.getCode();
  }

  public ErrorResponse(final ResponseCode code) {
    this.message = code.getMessage();
    this.status = code.getStatus().value();
    this.code = code.getCode();
    this.errors = new ArrayList<>();
  }

  public ErrorResponse(final ResponseCode code, final String message) {
    this.message = message;
    this.status = code.getStatus().value();
    this.code = code.getCode();
    this.errors = new ArrayList<>();
  }

  public static ErrorResponse of(final ResponseCode code, final BindingResult bindingResult) {
    return new ErrorResponse(code, FieldError.of(bindingResult));
  }

  public static ErrorResponse of(final ResponseCode code) {
    return new ErrorResponse(code);
  }

  public static ErrorResponse of(final ResponseCode code, String message) {
    return StringUtils.isNotBlank(message) ? new ErrorResponse(code , message) : new ErrorResponse(code);
  }

  public static ErrorResponse of(final ResponseCode code, final List<FieldError> errors) {
    return new ErrorResponse(code, errors);
  }

  public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
    final String value = e.getValue() == null ? "" : e.getValue().toString();
    final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
    return new ErrorResponse(ResponseCode.INVALID_TYPE_VALUE, errors);
  }


  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FieldError {

    private String field;
    private String value;
    private String reason;

    public static List<FieldError> of(final String field, final String value, final String reason) {
      List<FieldError> fieldErrors = new ArrayList<>();
      fieldErrors.add(new FieldError(field, value, reason));
      return fieldErrors;
    }

    private static List<FieldError> of(final BindingResult bindingResult) {
      return bindingResult.getFieldErrors().stream()
        .map(error -> new FieldError(
          error.getField(),
          error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
          error.getDefaultMessage()))
        .collect(Collectors.toList());
    }
  }
}
