package com.ex.shop.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    resourceHandlerRegistry.addResourceHandler("/static/**")
      .addResourceLocations("classpath:/static/");
    resourceHandlerRegistry.addResourceHandler("/upload/**")
      .addResourceLocations("classpath:/upload/");
    resourceHandlerRegistry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
    resourceHandlerRegistry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Bean
  public InternalResourceViewResolver defaultViewResolver() {
    return new InternalResourceViewResolver();
  }

  @Bean
  public ObjectMapper objectMapper(){
    return new ObjectMapper();
  }

}
