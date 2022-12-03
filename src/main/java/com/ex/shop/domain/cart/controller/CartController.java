package com.ex.shop.domain.cart.controller;

import com.ex.shop.common.auth.principal.PrincipalDetails;
import com.ex.shop.common.exception.BusinessException;
import com.ex.shop.common.model.ApiDataResponse;
import com.ex.shop.domain.cart.dto.CartDetailDto;
import com.ex.shop.domain.cart.dto.CartItemDto;
import com.ex.shop.domain.cart.dto.CartOrderDto;
import com.ex.shop.domain.cart.service.CartService;
import java.security.Principal;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CartController {

  private final CartService cartService;


  @PostMapping(value = "/cart")
  @ResponseBody
  public ApiDataResponse<?> order(
    @RequestBody @Valid CartItemDto cartItemDto,
    BindingResult bindingResult,
    @AuthenticationPrincipal PrincipalDetails principalDetails){

    String email = principalDetails.getMember().getEmail();
    Long cartItemId = cartService.addCart(cartItemDto, email);
    return ApiDataResponse.of(cartItemId);
  }

  @GetMapping(value = "/cart")
  public String orderHist(Principal principal, Model model) {
    List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
    model.addAttribute("cartItems", cartDetailList);
    return "cart/cartList";
  }


  @PatchMapping(value = "/cartItem/{cartItemId}")
  @ResponseBody
  public ApiDataResponse<?> updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count){

    if(count <= 0){
      throw new BusinessException("최소 1개이상 담아주세요");
    }

    cartService.updateCartItemCount(cartItemId, count);
    return ApiDataResponse.of(cartItemId);

  }

  @DeleteMapping(value = "/cartItem/{cartItemId}")
  @ResponseBody
  public ApiDataResponse<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId,
    @AuthenticationPrincipal PrincipalDetails principalDetails){

    if(!cartService.validateCartItem(cartItemId, principalDetails.getMember().getEmail())){
      throw new BusinessException("수정 권한이 없습니다.");
    }

    cartService.deleteCartItem(cartItemId);
    return ApiDataResponse.of(cartItemId);
  }


  @PostMapping(value = "/cart/orders")
  @ResponseBody
  public ApiDataResponse<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto,
    @AuthenticationPrincipal PrincipalDetails principalDetails){

    List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

    if(ObjectUtils.isEmpty(cartOrderDtoList)){
      throw new BusinessException("주문할 상품을 선택해주세요");
    }

    for (CartOrderDto cartOrder : cartOrderDtoList) {
      if(!cartService.validateCartItem(cartOrder.getCartItemId(), principalDetails.getMember().getEmail())){
        throw new BusinessException("주문 권한이 없습니다.");
      }
    }

    Long orderId = cartService.orderCartItem(cartOrderDtoList, principalDetails.getMember().getEmail());
    return ApiDataResponse.of(orderId);
  }
}
