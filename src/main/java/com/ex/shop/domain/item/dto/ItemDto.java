package com.ex.shop.domain.item.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

  private Long id;
  private String itemNm;
  private Integer price;
  private String itemDetail;
  private String sellStatCd;
  private LocalDateTime regTime;
  private LocalDateTime updateTime;
}
