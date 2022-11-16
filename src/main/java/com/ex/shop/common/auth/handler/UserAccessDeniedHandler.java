package com.ex.shop.common.security.handler;

import com.ex.shop.common.enums.ErrorMsgType;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
@Component
public class UserAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(
		HttpServletRequest request,
		HttpServletResponse response,
		AccessDeniedException accessDeniedException
	) throws IOException, ServletException {

		response.sendRedirect("/error-msg?errorCode="+ ErrorMsgType.ACCESS_DENIED.getCode());
	}
}
