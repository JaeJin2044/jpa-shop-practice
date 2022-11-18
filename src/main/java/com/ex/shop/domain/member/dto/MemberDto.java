package com.ex.shop.domain.member.dto;

import com.ex.shop.common.annotation.Password;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

  @NotBlank(message = "이름은 필수 입력값입니다.")
  private String name;

  @NotBlank(message = "이메일은 필수 입력값입니다.")
  @Email
  private String email;

  @Password(min = 8, max = 16)
  private String password;

  @NotBlank(message = "주소는 필수 입력값입니다.")
  private String address;

}
