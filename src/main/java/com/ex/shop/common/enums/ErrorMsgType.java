package com.ex.shop.common.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMsgType {
  ACCESS_DENIED("11","권한이 없는 페이지로 접근 및 요청하셨습니다.");

  String code;
  String msg;

  public static ErrorMsgType of(String code){
    return Arrays.stream(values())
      .filter(o -> o.getCode().equals(code))
      .findFirst()
      .orElse(null);
  }


  public static String getMsg(String code){
    ErrorMsgType type = ErrorMsgType.of(code);
    return type == null ? "" : type.getMsg();
  }

}
