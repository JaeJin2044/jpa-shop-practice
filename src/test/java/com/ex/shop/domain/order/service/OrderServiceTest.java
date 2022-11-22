package com.ex.shop.domain.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.enums.ItemSellStatus;
import com.ex.shop.domain.item.repository.ItemRepository;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import com.ex.shop.domain.order.dto.OrderDto;
import com.ex.shop.domain.order.entity.Order;
import com.ex.shop.domain.order.entity.OrderItem;
import com.ex.shop.domain.order.enums.OrderStatus;
import com.ex.shop.domain.order.repository.OrderRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  MemberRepository memberRepository;

  public Item saveItem(){
    Item item = new Item();
    item.setItemNm("테스트 상품");
    item.setPrice(10000);
    item.setItemDetail("테스트 상품 상세 설명");
    item.setItemSellStatus(ItemSellStatus.SELL);
    item.setStockNumber(100);
    return itemRepository.save(item);
  }

  public Member saveMember(){
    Member member = new Member();
    member.setEmail("test@test.com");
    return memberRepository.save(member);
  }

  @Test
  @DisplayName("주문 테스트")
  public void order(){
    Item item = saveItem();
    Member member = saveMember();

    OrderDto orderDto = new OrderDto();
    orderDto.setCount(10);
    orderDto.setItemId(item.getId());

    Long orderId = orderService.order(orderDto, member.getEmail());
    Order order = orderRepository.findById(orderId)
      .orElseThrow(EntityNotFoundException::new);

    List<OrderItem> orderItems = order.getOrderItems();

    int totalPrice = orderDto.getCount()*item.getPrice();

    assertEquals(totalPrice, order.getTotalPrice());
  }

//  @Test
//  @DisplayName("주문 취소 테스트")
//  public void cancelOrder(){
//    Item item = saveItem();
//    Member member = saveMember();
//
//    OrderDto orderDto = new OrderDto();
//    orderDto.setCount(10);
//    orderDto.setItemId(item.getId());
//    Long orderId = orderService.order(orderDto, member.getEmail());
//
//    Order order = orderRepository.findById(orderId)
//      .orElseThrow(EntityNotFoundException::new);
//    orderService.cancelOrder(orderId);
//
//    assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
//    assertEquals(100, item.getStockNumber());
//  }


}