package com.ex.shop.domain.test;

import com.ex.shop.common.enums.ResponseCode;
import com.ex.shop.common.exception.BusinessException;
import com.ex.shop.common.exception.EntityNotFoundException;
import com.ex.shop.common.exception.TestException;
import com.ex.shop.common.model.ApiDataResponse;
import com.ex.shop.domain.item.dto.ItemFormDto;
import com.ex.shop.domain.item.service.ItemService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

  private final ItemService itemService;

  @PostMapping("/data")
  public ApiDataResponse<?> list(@Valid @RequestBody ItemFormDto itemFormDto){
    return ApiDataResponse.of(itemService.getItemDtl(5L));
  }

  @PostMapping("/dat2")
  public ApiDataResponse<?> list2(){
    return ApiDataResponse.ok();
  }
}
