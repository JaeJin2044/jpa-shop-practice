package com.ex.shop.domain.order.dto;

import com.ex.shop.domain.order.entity.Order;
import com.ex.shop.domain.order.enums.OrderStatus;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderHistDto {

  public OrderHistDto(Order order){
    this.orderId = order.getId();
    this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    this.orderStatus = order.getOrderStatus();
  }

  //주문 아이디
  private Long orderId;
  //주문 날짜
  private String orderDate;
  //주문 상태
  private OrderStatus orderStatus;
  //주문 상품 리스트
  private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

  public void addOrderItemDto(OrderItemDto orderItemDto){
    orderItemDtoList.add(orderItemDto);
  }

}
