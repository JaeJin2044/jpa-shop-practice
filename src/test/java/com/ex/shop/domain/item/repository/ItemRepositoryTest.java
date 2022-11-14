package com.ex.shop.domain.item.repository;

import com.ex.shop.domain.item.enums.ItemSellStatus;
import com.ex.shop.domain.item.entity.Item;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest  //스프링부트에서 제공하는 어노테이션 실제 어플리케이션을 구동할때 처럼 모든 Bean을 IoC컨테이너에 등록한다
@TestPropertySource(locations = "classpath:application-test.yml")  // application.yml에 기재한 내용중에 같은 값이 있다면 해당 파일을 더 우선순위로 부여한다. (mysql -> h2)
class ItemRepositoryTest {

  @Autowired
  private ItemRepository itemRepository;

  @Test
  @DisplayName("상품 저장 테스트")
  public void createItemTest(){
    Item item = new Item();
    item.setName("테스트 상품");
    item.setPrice(10000);
    item.setItemDetail("테스트 상품 상세설명~~~");
    item.setItemSellStatus(ItemSellStatus.SELL);
    item.setStockNumber(100);
    item.setCreatedAt(LocalDateTime.now());
    item.setUpdatedAt(LocalDateTime.now());

    Item savedItem = itemRepository.save(item);

    System.out.println(savedItem.toString());


  }
}