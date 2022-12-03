package com.ex.shop.domain.order.service;

import com.ex.shop.common.exception.EntityNotFoundException;
import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.entity.ItemImg;
import com.ex.shop.domain.item.repository.ItemImgRepository;
import com.ex.shop.domain.item.repository.ItemRepository;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import com.ex.shop.domain.order.dto.OrderDto;
import com.ex.shop.domain.order.dto.OrderHistDto;
import com.ex.shop.domain.order.dto.OrderItemDto;
import com.ex.shop.domain.order.entity.Order;
import com.ex.shop.domain.order.entity.OrderItem;
import com.ex.shop.domain.order.repository.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final OrderRepository orderRepository;
  private final ItemImgRepository itemImgRepository;


  @Transactional
  public Long order(OrderDto orderDto, String email){

    Item item = itemRepository.findById(orderDto.getItemId())
      .orElseThrow(EntityNotFoundException::new);

    Member member = memberRepository.findByEmail(email)
      .orElse(null);

    List<OrderItem> orderItemList = new ArrayList<>();

    OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
    orderItemList.add(orderItem);

    Order order = Order.createOrder(member,orderItemList);
    orderRepository.save(order);

    return order.getId();
  }

  // 주문 목록을 조회하기 위한 로직
  @Transactional(readOnly = true)
  public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {

    List<Order> orders = orderRepository.findOrders(email, pageable); // 주문 목록을 조회

    Long totalCount = orderRepository.countOrder(email); // 주문 총 개수

    List<OrderHistDto> orderHistDtos = new ArrayList<>();

    for (Order order : orders) {
      OrderHistDto orderHistDto = new OrderHistDto(order);
      List<OrderItem> orderItems = order.getOrderItems();

      for (OrderItem orderItem : orderItems) { // entity -> dto

        ItemImg itemImg = itemImgRepository.findByItemIdAndReimgYn(orderItem.getItem().getId(), "Y"); // 대표상품인지 보는거 (상품 이력 페이지에 출력해야 하니까)

        OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl()); // entity-> dto

        orderHistDto.addOrderItemDto(orderItemDto);
      }
      orderHistDtos.add(orderHistDto);
    }

    return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
  }

  @Transactional(readOnly = true)
  public boolean validateOrder(Long orderId, String email){

    Member curMember = memberRepository.findByEmail(email)
      .orElseThrow(EntityNotFoundException::new);

    Order order = orderRepository.findById(orderId)
      .orElseThrow(EntityNotFoundException::new);
    Member savedMember = order.getMember();

    if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
      return false;
    }
    return true;
  }

  @Transactional
  public void cancelOrder(Long orderId){
    Order order = orderRepository.findById(orderId)
      .orElseThrow(EntityNotFoundException::new);
    order.cancelOrder();
  }

  @Transactional
  public Long orders(List<OrderDto> orderDtoList, String email){

    Member member = memberRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    List<OrderItem> orderItemList = new ArrayList<>();

    for (OrderDto orderDto : orderDtoList) {
      Item item = itemRepository.findById(orderDto.getItemId())
        .orElseThrow(javax.persistence.EntityNotFoundException::new);

      OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
      orderItemList.add(orderItem);
    }

    Order order = Order.createOrder(member, orderItemList);
    orderRepository.save(order);

    return order.getId();
  }
}
