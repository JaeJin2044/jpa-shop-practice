package com.ex.shop.domain.cart.entity;

import com.ex.shop.common.entity.BaseEntity;
import com.ex.shop.domain.item.entity.Item;
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
@Table(name="cart_item")
@Entity
public class CartItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cart_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="cart_id")
  private Cart cart;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  private int count;

  public static CartItem createCartItem(Cart cart, Item item, int count){
    CartItem cartItem = new CartItem();
    cartItem.setItem(item);
    cartItem.setCount(count);
    cartItem.setCart(cart);
    return cartItem;
  }

  public void updateCount(int count){
    this.count = count;
  }

  public void addCount(int count){
    this.count += count;
  }

}
