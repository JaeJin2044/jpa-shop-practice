package com.ex.shop.domain.cart.service;

import static org.junit.jupiter.api.Assertions.*;

import com.ex.shop.domain.cart.dto.CartItemDto;
import com.ex.shop.domain.cart.entity.CartItem;
import com.ex.shop.domain.cart.repository.CartItemRepository;
import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.enums.ItemSellStatus;
import com.ex.shop.domain.item.repository.ItemRepository;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.repository.MemberRepository;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class CartServiceTest {

  @Autowired
  ItemRepository itemRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  CartService cartService;

  @Autowired
  CartItemRepository cartItemRepository;

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
  @DisplayName("장바구니 담기 테스트")
  public void addCart(){
    Item item = saveItem();
    Member member = saveMember();

    CartItemDto cartItemDto = new CartItemDto();
    cartItemDto.setCount(5);
    cartItemDto.setItemId(item.getId());

    Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());
    CartItem cartItem = cartItemRepository.findById(cartItemId)
      .orElseThrow(EntityNotFoundException::new);

    assertEquals(item.getId(), cartItem.getItem().getId());
    assertEquals(cartItemDto.getCount(), cartItem.getCount());
  }

}