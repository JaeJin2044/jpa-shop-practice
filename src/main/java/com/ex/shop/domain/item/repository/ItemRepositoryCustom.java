package com.ex.shop.domain.item.repository;

import com.ex.shop.domain.item.dto.ItemSearchDto;
import com.ex.shop.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

  Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

}
