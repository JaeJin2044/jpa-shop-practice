package com.ex.shop.domain.item.repository;

import com.ex.shop.domain.item.entity.ItemImg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {


  List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

  // 상품 대표 이미지를 찾는 쿼리 메소드 추가
  // 하는 이유는, 구매 이력 페이지에서 주문 상품 대표 이미지 출력할려고
  ItemImg findByItemIdAndReimgYn(Long itemId, String repimgYn);

}
