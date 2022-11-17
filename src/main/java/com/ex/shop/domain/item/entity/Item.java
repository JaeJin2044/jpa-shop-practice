package com.ex.shop.domain.item.entity;

import com.ex.shop.common.entity.BaseEntity;
import com.ex.shop.domain.item.dto.ItemFormDto;
import com.ex.shop.domain.item.enums.ItemSellStatus;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="item")
@Entity
public class Item extends BaseEntity {

  /* 상품 코드*/
  @Id
  @Column(name="item_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 상품 명*/
  @Column(nullable = false, length = 255, name = "item_nm")
  private String itemNm;

  /* 가격 */
  @Column(name="price", nullable = false)
  private int price;

  /* 재고 수량*/
  @Column(nullable = false)
  private int stockNumber;

  /* 상품 상세 설명*/
  @Lob //Large Object
  @Column(nullable = false)
  private String itemDetail;

  /*상품 판매 상태*/
  @Enumerated(EnumType.STRING)
  private ItemSellStatus itemSellStatus;

  public void updateItem(ItemFormDto itemFormDto){
    this.itemNm = itemFormDto.getItemNm();
    this.price = itemFormDto.getPrice();
    this.stockNumber = itemFormDto.getStockNumber();
    this.itemDetail = itemFormDto.getItemDetail();
    this.itemSellStatus = itemFormDto.getItemSellStatus();
  }

}
