package com.ex.shop.domain.global.controller;

import com.ex.shop.common.enums.ErrorMsgType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GlobalController {

  @GetMapping({"/","/main"})
  public String main(){
    log.info("메인페이지");
    return "/main";
  }

  @GetMapping("/error-msg")
  public String errorMsg(@RequestParam("errorCode") String errorCode, Model model){

    model.addAttribute("errorCode",errorCode);
    model.addAttribute("errorMsg", ErrorMsgType.getMsg(errorCode));
    return "/error-msg";
  }
}
