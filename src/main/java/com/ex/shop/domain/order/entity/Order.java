package com.ex.shop.domain.order.entity;

import com.ex.shop.common.entity.BaseEntity;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.order.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_item_id")
  private List<OrderItem> orderItems = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private LocalDateTime orderDate;

  /**
   * 양방향
   * 엔티티를 양방향 관계로 설정하면 객체의 참조는 둘인데 외래키는 하나이므로 둘 중 누가 외래키를 관리할지를 정해야한다.
   * 1.연관관계의 주인이 외래키가 있는곳으로 설정
   * 2.연관관계의 주인이 외래키를 관리(등록,수정,삭제)
   * 3.주인이 아닌 쪽은 연관 관계 매핑시 mappedBy속성의 값으로 연관관계의 주인을 설정
   * 4.주인이 아닌 쪽은 읽기만 가능
   */

}













