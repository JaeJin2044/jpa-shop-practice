package com.ex.shop.common.service;

import com.ex.shop.common.auth.principal.PrincipalDetails;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareService implements AuditorAware<String> {

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
