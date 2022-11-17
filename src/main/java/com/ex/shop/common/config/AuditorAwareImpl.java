package com.ex.shop.common.config;

import com.ex.shop.common.auth.principal.PrincipalDetails;
import com.ex.shop.domain.member.entity.Member;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userId = "";
    try {
      if (authentication.getPrincipal() instanceof PrincipalDetails) {
        userId = ((PrincipalDetails) authentication.getPrincipal()).getMember().getName();
      }
    } catch (Exception e) {
      userId = "";
    }

    return Optional.of(userId);
  }
}
