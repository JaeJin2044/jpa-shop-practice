package com.ex.shop.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  /**
   * 세션에 객체를 Map으로 변환해서 담는다.
   *
   * @param keyName 세션 명
   * @param object  담을 객체
   */
  public static void setSession(String keyName, Object object) {
    Map<String, Object> map = mapper.convertValue(object, Map.class);
    Objects.requireNonNull(RequestUtils.getRequest()).getSession().setAttribute(keyName, map);
  }

  /**
   * 세션을 Map으로 읽어서 객체 타입으로 변환하여 조회한다.
   *
   * @param keyName 세션 명
   * @param clazz   변환할 객체 타입
   * @param <T>     제네릭
   * @return Object 변환된 객체
   */
  public static <T> T getSession(String keyName, Class<T> clazz) {
    Map<String, Object> map = (Map) Objects.requireNonNull(RequestUtils.getRequest()).getSession()
      .getAttribute(keyName);
    return mapper.convertValue(map, clazz);
  }

  /**
   * 세션을 Map 형식으로 조회한다.
   *
   * @param keyName 세션 명
   * @return Map
   */
  public static Map<String, Object> getSession(String keyName) {
    return getSession(keyName, Map.class);
  }


  /* #########################################################################################################  */


  /**
   * 세션에 저장된 Object 반환
   *
   * @param session HttpSession
   * @param name Session Key
   * @return null or Object
   */
  public static Object getAttribute(String name) {
    if(isSession()){
      return Objects.requireNonNull(RequestUtils.getRequest())
        .getSession().getAttribute(name);
    }
    return null;
  }

  /**
   * 세션에 저장된 Object 반환
   *
   * @param name Session Key
   * @param clazz 캐스팅 클래스
   * @return null or 캐스팅된 Object
   */
  @SuppressWarnings("unchecked")
  public static <T> T getAttribute(HttpSession session, String name, Class<T> clazz) {
    Object attribute = session.getAttribute(name);
    return (attribute == null) ? null : (T) attribute;
  }

  /**
   * 세션에 Object 저장
   *
   * @param name Session Key
   * @param object 저장될 Object
   */
  public static void setAttribute(String name, Object object) {
    if(isSession()){
      Objects.requireNonNull(RequestUtils.getRequest())
        .getSession().setAttribute(name,object);
    }
  }

  /**
   * 세션에 저장된 Object 삭제
   * @param name Session Key
   */
  public static void removeAttribute(String name) {
    if(isSession()){
      Objects.requireNonNull(RequestUtils.getRequest())
        .getSession().removeAttribute(name);
    }
  }

  /**
   * 세션 ID
   * @return Session ID
   */
  public static String getSessionId() {
    if(isSession()){
      return Objects.requireNonNull(RequestUtils.getRequest())
        .getSession().getId();
    }
    return null;
  }


  /**
   * 현재 Request의 Session의 존재 유무를 체크한다
   * @return
   */
  public static boolean isSession(){
    return Objects.requireNonNull(RequestUtils.getRequest()).getSession() != null ? true : false;
  }

}
