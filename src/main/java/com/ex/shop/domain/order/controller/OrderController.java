package com.ex.shop.domain.order.controller;

import com.ex.shop.common.auth.principal.PrincipalDetails;
import com.ex.shop.common.exception.BusinessException;
import com.ex.shop.common.model.ApiDataResponse;
import com.ex.shop.domain.order.dto.OrderDto;
import com.ex.shop.domain.order.dto.OrderHistDto;
import com.ex.shop.domain.order.service.OrderService;
import java.security.Principal;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping("/order")
  @ResponseBody
  public ApiDataResponse<?> order(@RequestBody @Valid OrderDto orderDto,
    @AuthenticationPrincipal PrincipalDetails principalDetails){

    String email = principalDetails.getMember().getEmail();
    Long orderId = orderService.order(orderDto,email);

    return ApiDataResponse.of(orderId);
  }

  // 구매이력 조회 get 페이지
  @GetMapping(value = {"/orders", "/orders/{page}"})
  public String orderHist(
    @PathVariable("page") Optional<Integer> page,
    @AuthenticationPrincipal PrincipalDetails principalDetails
    , Model model
  ){

    // 한 번에 가지고 올 주문 개수 10개!!!
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

    // 현재 로그인한 회원은 화면에 전달한 주문 목록 데이터를 리턴 값으로 받음 (이메일과 페이징 객체를 파라미터로 전달)
    Page<OrderHistDto> ordersHistDtoList = orderService.getOrderList(principalDetails.getMember().getEmail(), pageable);

    // ordersHistDtoList: 주문 목록 데이터
    // principal.getName(): 이메일(현재 로그인한 회원)
    // pageable: 페이징 객체?
    model.addAttribute("orders", ordersHistDtoList);
    model.addAttribute("page", pageable.getPageNumber());
    model.addAttribute("maxPage", 5);

    return "order/orderHist";
  }

  @PostMapping("/order/{orderId}/cancel")
  @ResponseBody
  public ApiDataResponse<?> cancelOrder(@PathVariable("orderId") Long orderId , Principal principal){

    if(!orderService.validateOrder(orderId, principal.getName())){
      throw new BusinessException("주문 취소 권한이 없습니다.");
    }

    orderService.cancelOrder(orderId);
    return ApiDataResponse.of(orderId);
  }
}
