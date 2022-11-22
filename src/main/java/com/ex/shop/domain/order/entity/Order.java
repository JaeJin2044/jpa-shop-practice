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

  // cascade:  부모가 바뀌면 자식도 바뀐다. 부모랑 자식이랑 연동된다.(실무 레벨 체크)
  // orphanRemoval = true 를 하면은 고아 객체를 지울 수 있다.(실무 레벨 체크)
  // 원래 디비에는 one to many 가 없다. 단방향이기 때문에! => 양방향 매핑을 위해 적음 (order - orderId)
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
    , orphanRemoval = true, fetch = FetchType.LAZY)
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

  public void addOrderItem(OrderItem orderItem) {
    // orderItems 에 주문 상품 정보들 넣어줌
    orderItems.add(orderItem);
    // orderItems 과 양방향 매핑이기 때문에 orderItem 에다가도 order 객체를 넣어줌 (orderItems 은 order 객체임)
    // 안넣어주면 order_item 테이블에 order_id(조인키)가 null로 insert됨
    orderItem.setOrder(this);
  }

  public static Order createOrder(Member member, List<OrderItem> orderItemList) {

    Order order = new Order();
    order.setMember(member); // 상품을 주문한 회원의 정보 setter

    for(OrderItem orderItem : orderItemList) {
      order.addOrderItem(orderItem);
      // 상품 페이지에서는 1개의 상품을 주문하지만, 장바구니에는 여러 상품을 주문할 수 있다.
      // 그래서 장바구니에 여러 상품을 담을 수 있게 리스트 형태로 파라미터 값을 받아야 한다.  파라미터는 아까 주문한 orderItem 임!
    }
    order.setOrderStatus(OrderStatus.ORDER); // 주문 상태를 ORDER 로 바꿈
    order.setOrderDate(LocalDateTime.now()); // 현재 시간을 주문 시간으로 바꿈
    return order;
  }

  // 총 주문 금액
  public int getTotalPrice() {
    int totalPrice = 0;
    for(OrderItem orderItem : orderItems){
      totalPrice += orderItem.getTotalPrice();
    }
    return totalPrice;
  }

  public void cancelOrder() {
    this.orderStatus = OrderStatus.CANCEL;
    for (OrderItem orderItem : orderItems) {
      orderItem.cancel();
    }
  }


}













