package com.ex.shop.domain.cart.repository;


import com.ex.shop.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

  Cart findByMemberId(Long memberId);

}
