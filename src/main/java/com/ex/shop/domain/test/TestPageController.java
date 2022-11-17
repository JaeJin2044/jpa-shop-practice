package com.ex.shop.domain.test;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping("/test/page")
public class TestPageController {


  @GetMapping("/test")
  public String test(){
    return "/test/test";
  }
}
