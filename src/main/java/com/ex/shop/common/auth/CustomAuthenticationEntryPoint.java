package com.ex.shop.common.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 모든 에러 발생시 해당 commence 메서드를 호출한다.
 * 처음 의도는 권한이 없는 페이지 접근시에 대한 처리를 하려고 헀으나, 모든 에러를 아래에서 처리하기 때문에
 * UserAccessDeniedHandler를 구현하여 사용
 */
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//  @Override
//  public void commence(
//    HttpServletRequest request,
//    HttpServletResponse response,
//    AuthenticationException authException
//  ) throws IOException, ServletException {
//
//    response.sendRedirect("/members/auth/error");
//  }
//}
