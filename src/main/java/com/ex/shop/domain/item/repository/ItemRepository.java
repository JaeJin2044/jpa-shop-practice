package com.ex.shop.domain.item.repository;

import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.entity.ItemImg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository<Item,Long> , QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

  List<Item> findByItemNm(String name);

  @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
  List<Item> findByItemDetail(String itemDetail);



}
