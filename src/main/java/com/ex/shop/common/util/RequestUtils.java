package com.ex.shop.common.util;

import java.util.Arrays;
import java.util.Objects;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class RequestUtils {

  /**
   * 현재 요청의 HttpServletRequest를 구한다.
   *
   * @return HttpServletRequest httpServletRequest 객체
   */
  public static HttpServletRequest getRequest() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(requestAttributes)
        || !(requestAttributes instanceof ServletRequestAttributes)) {
      return null;
    }
    return ((ServletRequestAttributes) requestAttributes).getRequest();
  }

  /**
   * 현재 요청의 HttpServletResponse를 구한다.
   *
   * @return HttpServletResponse
   */
  public static HttpServletResponse getResponse() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (Objects.isNull(requestAttributes)
        || !(requestAttributes instanceof ServletRequestAttributes)) {
      return null;
    }
    return ((ServletRequestAttributes) requestAttributes).getResponse();
  }

  /**
   * request 헤더에서 특정 이름의 값을 구한다.
   *
   * @param name 헤더 명
   * @return String 헤더 값
   */
  public static String getRequestHeader(String name) {
    HttpServletRequest httpServletRequest = getRequest();
    if (httpServletRequest != null) {
      return httpServletRequest.getHeader(name);
    } else {
      return null;
    }
  }

  /**
   * 사용자 아이피를 구한다.
   *
   * @return String 아이피 값
   */
  public static String getIp() {
    HttpServletRequest request = getRequest();
    String ip = Objects.requireNonNull(request).getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }

  /**
   * 모바일 앱(APP)에서 들어온 요청인지 여부를 반환한다.
   *
   * @return boolean
   */
  // TODO: 모바일 앱에서 전달되는 User Agent 키워드를 적용
  public static boolean isApp() {
    return getUserAgent().contains("XXXXXXX");
  }

  /**
   * 사용자 에이전트를 구한다.
   *
   * @return String 사용자 에이전트 값
   */
  public static String getUserAgent() {
    return Objects.requireNonNull(getRequest()).getHeader("User-Agent");
  }

  /**
   * request 쿠키 특정 이름의 값을 구한다.
   *
   * @param name 쿠키 명
   * @return String 쿠키 값
   */
  public static String getRequestCookie(String name) {
    try {
      return Arrays.stream(Objects.requireNonNull(getRequest()).getCookies())
          .filter(c -> c.getName().equals(name))
          .findFirst()
          .map(Cookie::getValue)
          .orElse(null);
    } catch (NullPointerException e) {
      log.debug("COOKIE_IS_EMPTY");
    }
    return null;
  }

  /**
   * 현재 RequestURL 및 QueryString을 구한다.
   * @return String
   */
  public static String getRequestURLWithQueryString() {

    HttpServletRequest reuqest = Objects.requireNonNull(getRequest());

    StringBuffer requestURL = reuqest.getRequestURL();
    String queryString = reuqest.getQueryString();

    if(StringUtils.isNotBlank(queryString)){
      requestURL.append("?").append(queryString);
    }
    return requestURL.toString();
  }

  /**
   * RequestURI 및 QueryString 획득
   * @return String
   */
  public static String getRequestURIWithQueryString() {

    HttpServletRequest reuqest = Objects.requireNonNull(getRequest());

    String requestURI = reuqest.getRequestURI();
    String queryString = reuqest.getQueryString();

    if(StringUtils.isNotBlank(queryString)){
      requestURI = requestURI + "?" + queryString;
    }
    return requestURI;
  }
}
