package com.ex.shop.domain.item.dto;

import com.ex.shop.domain.item.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

@Setter
@Getter
public class ItemImgDto {

  private Long id;
  private String imgName;
  private String oriImgName;
  private String imgUrl;
  private String reimgYn;

  private static ModelMapper modelMapper = new ModelMapper();

  public static ItemImgDto of (ItemImg itemImg){
    return modelMapper.map(itemImg,ItemImgDto.class);
  }

}
