package com.ex.shop.domain.item.repository;

import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.entity.QItem;
import com.ex.shop.domain.item.enums.ItemSellStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

@SpringBootTest  //스프링부트에서 제공하는 어노테이션 실제 어플리케이션을 구동할때 처럼 모든 Bean을 IoC컨테이너에 등록한다
@TestPropertySource(locations = "classpath:application-test.yml")  // application.yml에 기재한 내용중에 같은 값이 있다면 해당 파일을 더 우선순위로 부여한다. (mysql -> h2)
class ItemRepositoryTest {

  @Autowired
  private ItemRepository itemRepository;

  @PersistenceContext
  EntityManager em;   //영속성 컨텍스트를 사용하기위해 @PersistenceContext을 이용해 EntityManager 빈을 주입한다.

  @Test
  @DisplayName("상품 저장 테스트")
  public void createItemTest(){

    /*step1 */
    IntStream.range(0,10).forEach(i -> {
      Item item = new Item();
      item.setName("kk"+i);
      item.setPrice(1000+i);
      item.setItemDetail("이재진_품절"+i);
      item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
      item.setStockNumber(100);
      item.setCreatedAt(LocalDateTime.now());
      item.setUpdatedAt(LocalDateTime.now());
      itemRepository.save(item);
    });

    IntStream.range(0,10).forEach(i -> {
      Item item = new Item();
      item.setName("이재진"+i);
      item.setPrice(1000+i);
      item.setItemDetail("이재진_판매상태"+i);
      item.setItemSellStatus(ItemSellStatus.SELL);
      item.setStockNumber(100);
      item.setCreatedAt(LocalDateTime.now());
      item.setUpdatedAt(LocalDateTime.now());
      itemRepository.save(item);
    });


  }

  @Test
  @DisplayName("상품명 조회 테스트")
  public void findByNameTest(){
    this.createItemTest();
    List<Item> items = itemRepository.findAll();
    items.forEach(System.out::println);
  }

  @Test
  @DisplayName("@Query를 이용한 테스트")
  public void findByItemDetailTest(){
    this.createItemTest();
    List<Item> itemList = itemRepository.findByItemDetail("jay");
    System.out.println("Query를 이용한 테스트 Method");
    itemList.forEach(System.out::println);
  }

  @Test
  @DisplayName("QueryDsl 조회테스트1")
  public void queryDslTest(){
    this.createItemTest();
    /**
     * JPAQueryFactory를 이용하게 쿼리를 동적으로 생성한다.
     * 생성자의 파라미터로 EntityManger 객체를 넣어준다.
     */
    JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
    //QueryDsl을 통해 쿼리를 생성하기 위해 플러그인을 통해 자동으로 생성된 Qitem 객체를 이용한다.
    QItem qItem = QItem.item;

    JPAQuery<Item> query = jpaQueryFactory.selectFrom(qItem)
      .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
      .where(qItem.itemDetail.like("%"+"상세"+"%"))
      .orderBy(qItem.price.desc());

    /**
     * JPAQuery 메소드중 하나인 fetch를 이용해서 쿼리 결과를 리스트로 반환한다.
     * fetch() 메소드 실행 시점에 쿼리문이 실행된다.
     * JPAQuery에서 결과를 반환하는 메소드는 아래를 참고
     * fetch() -> 조회 결과 리스트 반환
     * fetchOne -> 조회 대상이 1건인 경우 제네릭으로 지정한 타입 반환
     * fetchFirest() -> 조회 대상중 1건만 반환
     * fetchCount() -> 조회 대상 개수 반환
     * fetchResults() -> 조회한 리스트와 전채 개수를 포함한 QueryResults 반환
     */

    List<Item> itemList = query.fetch();
    System.out.println("QueryDsl 1 테스트");
    itemList.forEach(System.out::println);

  }

  @Test
  @DisplayName("QueryDsl_조회_테스트2")
  public void queryDeslTest2(){
    this.createItemTest();

    //BooleanBuilder는 쿼리에 들어갈 조건을 만들어주는 빌더
    //Predicate를 구현 -> 메소드 체인형식으로 사용
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QItem item = QItem.item;
    String itemDetail = "이재진";
    int price = 1004;
    String itemSellStat = "SELL";

    booleanBuilder.and(item.itemDetail.like("%"+itemDetail+"%"));
    booleanBuilder.and(item.price.gt(price));

    //검색 파라미터가 SELL이라면 검색 조건 추가
    if(StringUtils.equals(itemSellStat,ItemSellStatus.SELL)){
      booleanBuilder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
    }

    Pageable pageable = PageRequest.of(0,5);
    Page<Item> itemPagingResult = itemRepository.findAll(booleanBuilder,pageable);

    System.out.println("total >> "+itemPagingResult.getTotalElements());
    List<Item> items = itemPagingResult.getContent();
    items.forEach(System.out::println);
  }

}




















