package com.ex.shop.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ObjectMapper objectMapper(){
    // 객체 항목중 LocalDateTime이 있는 경우 직렬화 또는 역직렬화를 못하는 현상 해결
    return new ObjectMapper().registerModule(new JavaTimeModule());
  }
}
