package com.ex.shop.domain.item.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class ItemController {

  @GetMapping("/sample")
  @ResponseBody
  public String smaple(){
    log.info("SAMPLE()");
    return "sample";
  }
}
