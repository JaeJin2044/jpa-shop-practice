package com.ex.shop.common.config;

import com.ex.shop.common.security.CustomAuthenticationEntryPoint;
import com.ex.shop.common.security.handler.UserAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 웹보안 활성화를 위한 annotation
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig{

  @Bean
  @Order(0)
  public SecurityFilterChain resourcesSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
      .requestMatchers(matchers -> matchers
        .antMatchers("/static/**", "/favicon.ico"))
      .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
      .requestCache(RequestCacheConfigurer::disable)
      .securityContext(AbstractHttpConfigurer::disable)
      .sessionManagement(AbstractHttpConfigurer::disable)
      .build();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

    String loginPage = "/members/login";
    String loginProcessingUrl = "/members/login/process";
    String successForwardUrl = "/members/login/success";
    String failureUrl = "/members/login/error";
    //String invalidSessionUrl = "/members/auth/invalid";
    String logoutUrl = "/members/logout";

    httpSecurity.csrf(AbstractHttpConfigurer::disable)
      .headers(header -> header.frameOptions().sameOrigin())
      .authorizeRequests(authorize -> authorize
        .antMatchers("/members/**", "/error/**","/item/**","/images/**","/main","/","/error-msg")
        .permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")   // admin 권한만 접근가능
        .anyRequest()
        .authenticated())
      .formLogin(form -> form
        .loginPage(loginPage)
        .usernameParameter("email")
        .passwordParameter("password")
        .loginProcessingUrl(loginProcessingUrl)
        .successForwardUrl(successForwardUrl)
        .failureForwardUrl(failureUrl)
        .permitAll())
      .sessionManagement(session -> session
        .invalidSessionUrl(loginPage))
      .exceptionHandling(error -> error
        .accessDeniedHandler(new UserAccessDeniedHandler())
        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
      .logout(logout -> logout
        .logoutRequestMatcher(new AntPathRequestMatcher(logoutUrl))
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .logoutSuccessUrl(loginPage)
        .permitAll()
      );

    return httpSecurity.build();
  }
}
