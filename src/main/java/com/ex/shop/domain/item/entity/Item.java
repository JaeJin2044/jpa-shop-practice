package com.ex.shop.domain.item.entity;

import com.ex.shop.domain.item.enums.ItemSellStatus;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
public class Item {

  /* 상품 코드*/
  @Id
  @Column(name="item_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /* 상품 명*/
  @Column(nullable = false, length = 50)
  private String name;

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

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;


}
