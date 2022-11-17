package com.ex.shop.domain.item.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
@Table(name = "item_img")
@Entity
public class ItemImg {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_img_id")
  private Long id;

  /* 이미지 파일명 */
  private String imgName;
  /* 원본 이미지 파일명*/
  private String oriImgName;
  /* 이미지 고회 경로*/
  private String imgUrl;
  /* 대표 이미지 여부*/
  private String reimgYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  public void updateItemImg(
    String oriImgName,
    String imgName,
    String imgUrl
  ){
    this.oriImgName = oriImgName;
    this.imgName = imgName;
    this.imgUrl = imgUrl;
  }
}
