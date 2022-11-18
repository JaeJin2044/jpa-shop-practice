package com.ex.shop.domain.member.service;

import com.ex.shop.common.exception.BusinessException;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Member saveMember(Member member){
    validateDuplicateMember(member);
    return memberRepository.save(member);
  }


  private void validateDuplicateMember(Member member) {
    memberRepository.findByEmail(member.getEmail())
      .ifPresent(o -> {
        throw new BusinessException("이미 가입된 이메일입니다");
      });
  }
}
