package com.ex.shop.domain.order.dto;

import com.ex.shop.domain.order.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDto {

  // 주문상품, 이미지경로를 파라미터로 받음
  public OrderItemDto(OrderItem orderItem, String imgUrl){
    this.itemNm = orderItem.getItem().getItemNm();
    this.count = orderItem.getCount();
    this.orderPrice = orderItem.getOrderPrice();
    this.imgUrl = imgUrl;
  }

  //상품명
  private String itemNm;
  //주문 수량
  private int count;
  //주문 금액
  private int orderPrice;
  //상품 이미지 경로
  private String imgUrl;

}
