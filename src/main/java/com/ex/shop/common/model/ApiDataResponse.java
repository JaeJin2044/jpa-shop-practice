package com.ex.shop.common.model;


import com.ex.shop.common.enums.ResponseCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class ApiDataResponse<T> extends ErrorResponse {

  private final T data;

  public ApiDataResponse(T data) {
    super(ResponseCode.SUCCESS);
    this.data = data;
  }

  public static <T> ApiDataResponse<T> of(T data){
    return new ApiDataResponse<>(data);
  }

  public static <T> ApiDataResponse<T> ok(){
    return new ApiDataResponse<>(null);
  }

}
