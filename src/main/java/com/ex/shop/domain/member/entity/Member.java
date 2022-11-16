package com.ex.shop.domain.member.entity;

import com.ex.shop.domain.member.dto.MemberDto;
import com.ex.shop.domain.member.enums.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="member")
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  private String password;

  private String address;

  //Enum은 기본적으로 순서가 저장된다, 순서가 바뀔경우 문제가 생길수 있으니 항상 STRING옵션을 사용한다.
  @Enumerated(EnumType.STRING)
  private Role role;

  public static Member of(MemberDto dto, PasswordEncoder passwordEncoder){
    return Member.builder()
      .name(dto.getName())
      .email(dto.getEmail())
      .address(dto.getAddress())
      .password(passwordEncoder.encode(dto.getPassword()))
      .role(Role.ROLE_USER)
      .build();
  }
}













