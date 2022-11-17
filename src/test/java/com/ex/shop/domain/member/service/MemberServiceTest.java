package com.ex.shop.domain.member.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.ex.shop.domain.member.dto.MemberDto;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.enums.Role;
import com.ex.shop.domain.member.repository.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest  //스프링부트에서 제공하는 어노테이션 실제 어플리케이션을 구동할때 처럼 모든 Bean을 IoC컨테이너에 등록한다
@TestPropertySource(locations = "classpath:application-test.yml")  // application.yml에 기재한 내용중에 같은 값이 있다면 해당 파일을 더 우선순위로 부여한다. (mysql -> h2)
class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


  public Member createMember(){
    MemberDto memberDto = new MemberDto();
    memberDto.setName("이재진_어드민");
    memberDto.setAddress("서울시 중구 신당동");
    memberDto.setEmail("jay.lee@d2.co.kr");
    memberDto.setPassword("tntlfh1!2@");
    Member member = Member.of(memberDto, passwordEncoder);
    member.setRole(Role.ROLE_ADMIN);
    return member;
  }


  @Test
  @DisplayName("회원가입_테스트")
  public void saveMemberTest(){
    Member member = createMember();
    Member savedMember = memberService.saveMember(member);

    List<Member> members = memberRepository.findAll();

    //값이 같은지 확인
    assertEquals(member.getEmail(), savedMember.getEmail());
    assertEquals(member.getName(), savedMember.getName());
    assertEquals(member.getAddress(), savedMember.getAddress());
    assertEquals(member.getPassword(), savedMember.getPassword());
    assertEquals(member.getRole(), savedMember.getRole());
  }

  @Test
  @DisplayName("auditing_테스트")
  @WithMockUser(username = "jay", roles = "ADMIN")
  public void auditingTest(){
    Member member = createMember();
    Member savedMember = memberService.saveMember(member);

    List<Member> members = memberRepository.findAll();

    //값이 같은지 확인
    assertEquals(member.getEmail(), savedMember.getEmail());
    assertEquals(member.getName(), savedMember.getName());
    assertEquals(member.getAddress(), savedMember.getAddress());
    assertEquals(member.getPassword(), savedMember.getPassword());
    assertEquals(member.getRole(), savedMember.getRole());
  }

  @Test
  @DisplayName("중복_회원가입_테스트")
  public void saveDuplicateMemberTest(){
    Member member1 = createMember();
    Member member2 = createMember();

    Throwable e = assertThrows(IllegalStateException.class, () -> {
      memberService.saveMember(member2);
    });

    assertEquals("이미가입된 회원입니다.",e.getMessage());


  }


  @Test
  @DisplayName("유저조회_테스트")
  public void findMembersTest(){
    List<Member> members = memberRepository.findAll();
    members.forEach(System.out::println);
  }
}













