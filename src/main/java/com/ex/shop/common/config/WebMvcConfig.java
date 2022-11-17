package com.ex.shop.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${uploadPath}")
  private String uploadPath;


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry resourceHandlerRegistry) {

    resourceHandlerRegistry.addResourceHandler("/static/**")
      .addResourceLocations("classpath:/static/");
    resourceHandlerRegistry.addResourceHandler("/images/**")   // /images로 시작하는 경우 uploadPath에 설정한 폴더 기준으로 읽도록 설정
      .addResourceLocations(uploadPath);    //로컬 컴퓨터에 저장된 파일을 읽어올 root경로 설정

/*    resourceHandlerRegistry.addResourceHandler("/upload/**")
      .addResourceLocations("classpath:/upload/");*/
    resourceHandlerRegistry.addResourceHandler("/favicon.ico").addResourceLocations("/favicon.ico");
    resourceHandlerRegistry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }

  @Bean
  public InternalResourceViewResolver defaultViewResolver() {
    return new InternalResourceViewResolver();
  }

}
