package com.ex.shop.domain.item.repository;

import com.ex.shop.domain.item.entity.ItemImg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImgRepository extends JpaRepository<ItemImg,Long> {


  List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

}
