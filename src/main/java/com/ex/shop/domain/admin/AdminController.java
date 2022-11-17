package com.ex.shop.domain.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

  @GetMapping("test")
  public String adminTest(){
    return "/admin/test";
  }
}
