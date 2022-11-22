package com.ex.shop.domain.global.controller;

import com.ex.shop.common.enums.ErrorMsgType;
import com.ex.shop.domain.global.dto.MainItemDto;
import com.ex.shop.domain.item.dto.ItemSearchDto;
import com.ex.shop.domain.item.service.ItemService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GlobalController {

  private final ItemService itemService;

  @GetMapping({"/","/main"})
  public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model){

    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,6);
    Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto,pageable);

    model.addAttribute("items",items);
    model.addAttribute("itemSearchDto", itemSearchDto);
    model.addAttribute("maxPage",5);
    return "/main";
  }

  @GetMapping("/error-msg")
  public String errorMsg(@RequestParam("errorCode") String errorCode, Model model){

    model.addAttribute("errorCode",errorCode);
    model.addAttribute("errorMsg", ErrorMsgType.getMsg(errorCode));
    return "/error-msg";
  }


}
