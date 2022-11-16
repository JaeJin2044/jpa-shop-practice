package com.ex.shop.common.auth.principal;

import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("username :: {}",username);
    log.info("PrincipalDetilsService loadUserByUsername()");

    Optional<Member> optMember = memberRepository.findByEmail(username);
    Member member = optMember.orElseThrow(() -> new UsernameNotFoundException("존재 하지 않는 회원입니다."));
    return new PrincipalDetails(member);
  }
}
