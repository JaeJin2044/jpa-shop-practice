package com.ex.shop.common.security.auth;

import com.ex.shop.domain.member.entity.Member;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
public class PrincipalDetails implements UserDetails {

  private Member member;

  public PrincipalDetails(Member member){
    this.member = member;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(member.getRole().toString()));
    return authorities;
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    return member.getEmail();
  }

  //계정 기간 유효성 여부
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //계정 잠김 여부
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  //계정 활성화 여부
  @Override
  public boolean isEnabled() {
    return true;
  }
}
