package com.ex.shop.common.auth;


import com.ex.shop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final PrincipalDetailService principalDetilsService;
  private final PasswordEncoder passwordEncoder;
  private final MemberRepository memberRepository;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.info("CustomAuthenticationProvider authenticate()");

    String email = (String) authentication.getPrincipal();
    String password = (String) authentication.getCredentials();

    PrincipalDetails user = (PrincipalDetails) principalDetilsService.loadUserByUsername(email);

    if(!passwordEncoder.matches(password,user.getPassword())){
      throw new BadCredentialsException("비밀번호가 올바르지 않습니다.");
    }

    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
      = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    return usernamePasswordAuthenticationToken;
  }


  /**
   * 위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
   * null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주
   */
  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

}
