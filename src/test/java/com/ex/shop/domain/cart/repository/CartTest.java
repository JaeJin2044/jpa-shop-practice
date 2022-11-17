package com.ex.shop.domain.cart.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ex.shop.domain.cart.entity.Cart;
import com.ex.shop.domain.cart.repository.CartRepository;
import com.ex.shop.domain.member.dto.MemberDto;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.enums.Role;
import com.ex.shop.domain.member.repository.MemberRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
class CartTest {

  @Autowired
  CartRepository cartRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PersistenceContext
  EntityManager em; // 아줌마 // 엔티티는 과자, 매니저는 아줌마, 팩토리는 공장 // 영속성 컨텍스트: 엔티티를 영구히 저장하는 환경 _램! (영속성 컨텍스트를 관리하는 모든 엔티티 매니저가 초기화 및 종료되지 않는 한)

  public Member createMember(){
    MemberDto memberDto = new MemberDto();
    memberDto.setName("홍길동");
    memberDto.setPassword("dlwowls1!");
    memberDto.setAddress("서울시 중구");
    memberDto.setEmail("shzhem764@naver.com");
    Member member = Member.of(memberDto, passwordEncoder);
    member.setRole(Role.ROLE_ADMIN);
    return member;
  }

  // 영속성: 기본적으로 컴퓨터 공학에서 영속성이라고 하면 비휘발성

  @Test
  @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
  public void findCartAndMemberTest(){
    Member member = createMember();
    memberRepository.save(member);

    Cart cart = new Cart();
    cart.setMember(member);
    cartRepository.save(cart);

    em.flush(); // JPA는 영속성 컨텍스트에 데이터를 저장 후 트랜잭션이 끝날때 flush()를 호출하여 데이터베이스에 반영한다.
    em.clear(); // 영속성 컨텍스트에서 엔티티를 비워준다.  왜 비워주냐면 db 에서 장바구니 엔티티를 가지고 올 때 회원 엔티티도 같이 가져오는지 확인하기위하여

    Cart savedCart = cartRepository.findById(cart.getId())
      .orElseThrow(EntityNotFoundException::new);

    Member mem =  savedCart.getMember();

    System.out.println(savedCart.toString());

    assertEquals(savedCart.getMember().getId(), member.getId());
  }

}