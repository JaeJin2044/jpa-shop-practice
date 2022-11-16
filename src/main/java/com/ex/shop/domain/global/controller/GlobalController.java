package com.ex.shop.domain.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GlobalController {

  @GetMapping({"/","/main"})
  public String main(){
    log.info("메인페이지");
    return "/main";
  }
}
