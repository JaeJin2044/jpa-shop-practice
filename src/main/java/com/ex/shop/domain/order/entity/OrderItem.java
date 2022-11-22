package com.ex.shop.domain.order.entity;

import com.ex.shop.common.entity.BaseEntity;
import com.ex.shop.domain.item.entity.Item;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice; // 주문가격

  private int count; // 수량

  // 주문할 상품,주문 수량으로 orderItem 객체 생성
  public static OrderItem createOrderItem(Item item, int count) {

    OrderItem orderItem = new OrderItem();
    orderItem.setItem(item);
    orderItem.setCount(count);
    orderItem.setOrderPrice(item.getPrice());

    item.removeStock(count); // 상품 재고 수량에서 주문 수량을 뺌
    return orderItem;
  }

  public void cancel(){
    this.getItem().removeStock(count);
  }

  public int getTotalPrice(){
    return orderPrice * count;
  }

}
