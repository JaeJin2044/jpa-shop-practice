package com.ex.shop.domain.order.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.enums.ItemSellStatus;
import com.ex.shop.domain.item.repository.ItemRepository;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import com.ex.shop.domain.order.entity.Order;
import com.ex.shop.domain.order.entity.OrderItem;
import com.ex.shop.domain.order.enums.OrderStatus;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
@Transactional
class OrderRepositoryTest {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  OrderItemRepository orderItemRepository;

  @PersistenceContext
  EntityManager em;



  public Order careteOrder(){
    Order order = new Order();

    for(int i = 0; i < 3; i++){
      Item item = this.createItem();
      itemRepository.save(item);

      OrderItem orderItem = new OrderItem();
      orderItem.setItem(item);
      orderItem.setCount(10);
      orderItem.setOrderPrice(1000);
      orderItem.setOrder(order);
      order.getOrderItems().add(orderItem);
    }
    Member member = new Member();
    memberRepository.save(member);
    order.setMember(member);
    orderRepository.save(order);
    return order;
  }


  public Item createItem(){
    Item item = new Item();
    item.setItemNm("테스트 상품");
    item.setPrice(10000);
    item.setItemDetail("상세설명");
    item.setItemSellStatus(ItemSellStatus.SELL);
    item.setStockNumber(100);
    item.setCreatedAt(LocalDateTime.now());
    item.setUpdatedAt(LocalDateTime.now());
    return item;
  }

  public OrderItem createOrderItem() {
    OrderItem orderItem = new OrderItem();
    orderItem.setCreatedAt(LocalDateTime.now());
    orderItem.setUpdatedAt(LocalDateTime.now());
    orderItem.setOrderPrice(10000);

    Order order2 = this.createOrder2();

    orderRepository.save(order2);
    em.flush();
    orderItem.setOrder(order2);
    return orderItem;
  }

  public Order createOrder2(){
    Order order = new Order();
    order.setOrderStatus(OrderStatus.ORDER);
    return order;
  }


  @Test
  @DisplayName("영속성_전이_테스트")
  public void cascadeTest(){
    Order order = new Order();

    for(int i = 0; i < 3; i++){
      Item item = this.createItem();
      itemRepository.save(item);

      OrderItem orderItem = new OrderItem();
      orderItem.setItem(item);
      orderItem.setCount(10);
      orderItem.setOrderPrice(1000);
      orderItem.setOrder(order);

      order.getOrderItems().add(orderItem);
    }

    orderRepository.saveAndFlush(order);

    em.clear();

    Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);

    assertEquals(3,savedOrder.getOrderItems().size());

  }

  @Test
  @DisplayName("고아객체 제거 테스트")
  public void orphanRemovalTest(){
    Order order = this.careteOrder();
    order.getOrderItems().remove(0);
    em.flush();;
  }


  @Test
  @DisplayName("단방향 테스트")
  public void test1(){
    OrderItem orderItem = this.createOrderItem();
    orderItemRepository.save(orderItem);
  }
}







