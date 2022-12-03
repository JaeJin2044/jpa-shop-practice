package com.ex.shop.domain.cart.repository;

import com.ex.shop.domain.cart.dto.CartDetailDto;
import com.ex.shop.domain.cart.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

  CartItem findByCartIdAndItemId(Long cartId, Long itemId);


  @Query("select new com.ex.shop.domain.cart.dto.CartDetailDto("
    + "ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
    "from CartItem ci, ItemImg im " +
    "join ci.item i " +
    "where ci.cart.id = :cartId " +
    "and im.item.id = ci.item.id " +
    "and im.reimgYn = 'Y' " +
    "order by ci.createdAt desc"
  )

  List<CartDetailDto> findCartDetailDtoList(Long cartId);

}
