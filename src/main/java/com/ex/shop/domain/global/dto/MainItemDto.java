package com.ex.shop.domain.global.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainItemDto {

  private Long id;

  private String itemNm;

  private String itemDetail;

  private String imgUrl;

  private Integer price;

  @QueryProjection //이 어노테이션을 쓰면은 dto 로 객체를 변환할 수 있다.
  public MainItemDto(Long id, String itemNm, String itemDetail, String imgUrl,Integer price){
    this.id = id;
    this.itemNm = itemNm;
    this.itemDetail = itemDetail;
    this.imgUrl = imgUrl;
    this.price = price;
  }
}
