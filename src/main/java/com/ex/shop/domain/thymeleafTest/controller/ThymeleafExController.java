package com.ex.shop.domain.thymeleafTest.controller;

import com.ex.shop.domain.item.dto.ItemDto;
import com.ex.shop.domain.item.entity.Item;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/thymeleaf")
@Slf4j
public class ThymeleafExController {

  @GetMapping("/ex01")
  public String ex01(Model model){
    log.info("###############ex01()");
    model.addAttribute("data1","ㄴㅇ리ㅏㄴ아러");
    return "thymeleafEx/ex01";
  }

  /* DTO 매핑 테스트 단일 객체 */
  @GetMapping("/ex02")
  public String ex02(Model model){
    log.info("###############ex02()");
    ItemDto itemDto = ItemDto.builder()
      .itemDetail("상품 상세 설명")
      .itemNm("테스트상품1")
      .price(10000)
      .build();
    model.addAttribute("itemDto",itemDto);
    return "thymeleafEx/ex02";
  }

  /* DTO 매핑 테스트 리스트*/
  @GetMapping("/ex03")
  public String ex03(Model model){
    log.info("###############ex03()");

    List<ItemDto> list = new ArrayList<>();

    IntStream.range(0,10).forEach(i -> {
      ItemDto itemDto = ItemDto.builder()
        .itemDetail("상품 상세 설명"+i)
        .itemNm("테스트상품"+i)
        .price(10000*i)
        .build();
        list.add(itemDto);
    });
    model.addAttribute("list", list);
    return "thymeleafEx/ex03";
  }

}
