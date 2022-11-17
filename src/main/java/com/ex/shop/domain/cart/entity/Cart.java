package com.ex.shop.domain.cart.entity;

import com.ex.shop.domain.member.entity.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
@Entity
public class Cart {

  @Id
  @Column(name="cart_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //@OneToOne(fetch = FetchType.EAGER) 아래의 경우 fetch type이 EAGER(즉시로딩) 으로 설정되어있다.
  //JoinColumn이 있는 테이블에 외래키 컬럼이 추가된다고 생각 하자
  //@OneToOne(fetch = FetchType.EAGER)
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="member_id")
  private Member member;

}
