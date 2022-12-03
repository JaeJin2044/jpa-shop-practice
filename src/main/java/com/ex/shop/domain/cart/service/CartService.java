package com.ex.shop.domain.cart.service;

import com.ex.shop.common.exception.EntityNotFoundException;
import com.ex.shop.domain.cart.dto.CartDetailDto;
import com.ex.shop.domain.cart.dto.CartItemDto;
import com.ex.shop.domain.cart.dto.CartOrderDto;
import com.ex.shop.domain.cart.entity.Cart;
import com.ex.shop.domain.cart.entity.CartItem;
import com.ex.shop.domain.cart.repository.CartItemRepository;
import com.ex.shop.domain.cart.repository.CartRepository;
import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.repository.ItemRepository;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import com.ex.shop.domain.order.dto.OrderDto;
import com.ex.shop.domain.order.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;
  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final OrderService orderService;


  @Transactional
  public Long addCart(CartItemDto cartItemDto, String email){

    Item item = itemRepository.findById(cartItemDto.getItemId())
      .orElseThrow(EntityNotFoundException::new);

    Member member = memberRepository.findByEmail(email)
      .orElseThrow(EntityNotFoundException::new);

    Cart cart = cartRepository.findByMemberId(member.getId());

    //멤버의 장바구니가 존재하지 않았다면 create
    if(cart == null){
      cart = Cart.createCart(member);
      cartRepository.save(cart);
    }

    CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(
      cart.getId(), item.getId());

    //이미 등록된 장바구니 아이템은 카운트를 더해준다.
    if(savedCartItem != null){
      savedCartItem.addCount(cartItemDto.getCount());
      return savedCartItem.getId();
    }

    CartItem cartItem  = CartItem.createCartItem(cart,item,cartItemDto.getCount());
    cartItemRepository.save(cartItem);
    return cartItem.getId();
  }

  @Transactional(readOnly = true)
  public List<CartDetailDto> getCartList(String email){

    List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

    Member member = memberRepository.findByEmail(email)
      .orElseThrow(EntityNotFoundException::new);

    Cart cart = cartRepository.findByMemberId(member.getId());
    if(cart == null){
      return cartDetailDtoList;
    }

    cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());
    return cartDetailDtoList;
  }

  @Transactional(readOnly = true)
  public boolean validateCartItem(Long cartItemId, String email){
    Member curMember = memberRepository.findByEmail(email)
      .orElseThrow(EntityNotFoundException::new);

    CartItem cartItem = cartItemRepository.findById(cartItemId)
      .orElseThrow(EntityNotFoundException::new);

    Member savedMember = cartItem.getCart().getMember();

    if(!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())){
      return false;
    }

    return true;
  }

  @Transactional
  public void updateCartItemCount(Long cartItemId, int count){
    CartItem cartItem = cartItemRepository.findById(cartItemId)
      .orElseThrow(EntityNotFoundException::new);
    cartItem.updateCount(count);
  }

  @Transactional
  public void deleteCartItem(Long cartItemId) {
    CartItem cartItem = cartItemRepository.findById(cartItemId)
      .orElseThrow(EntityNotFoundException::new);
    cartItemRepository.delete(cartItem);
  }


  @Transactional
  public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){

    List<OrderDto> orderDtoList = new ArrayList<>();

    for (CartOrderDto cartOrderDto : cartOrderDtoList) {

      CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
        .orElseThrow(EntityNotFoundException::new);

      OrderDto orderDto = new OrderDto();
      orderDto.setItemId(cartItem.getItem().getId());
      orderDto.setCount(cartItem.getCount());
      orderDtoList.add(orderDto);
    }

    Long orderId = orderService.orders(orderDtoList, email);

    for (CartOrderDto cartOrderDto : cartOrderDtoList) {
      CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
        .orElseThrow(javax.persistence.EntityNotFoundException::new);
      cartItemRepository.delete(cartItem);
    }
    return orderId;
  }



}

















