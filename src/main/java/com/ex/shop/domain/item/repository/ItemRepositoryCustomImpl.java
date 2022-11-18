package com.ex.shop.domain.item.repository;


import com.ex.shop.domain.item.dto.ItemSearchDto;
import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.entity.QItem;
import com.ex.shop.domain.item.enums.ItemSellStatus;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

  private JPAQueryFactory jpaQueryFactory;

  public ItemRepositoryCustomImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
//    QueryResults<Item> results = jpaQueryFactory
//      .selectFrom(QItem.item)
//      .where(regDsAfter(itemSearchDto.getSearchDateType()),
//             searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
//              searchByLike(itemSearchDto.getSearchBy(),itemSearchDto.getSearchQuery()))
//      .orderBy(QItem.item.id.desc())
//      .offset(pageable.getOffset())
//      .limit(pageable.getPageSize())
//      .fetchResults(); //depreated 됨으로 인해 변경
    List<Item> results = jpaQueryFactory
      .selectFrom(QItem.item)
      .where(regDsAfter(itemSearchDto.getSearchDateType()),
        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
      )
      .orderBy(QItem.item.id.desc())
      .offset(pageable.getOffset()).limit(pageable.getPageSize())
      .fetch(); //depreated 됨으로 인해 변경


    long total = jpaQueryFactory
      .selectFrom(QItem.item)
      .where(regDsAfter(itemSearchDto.getSearchDateType()),
        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
      )
      .fetch().size();

    return new PageImpl<>(results, pageable, total); //조회한 결과를 Page클래스의 구현체이 PageImpl 객체로 반환합니다.
  }


  /**
   * 상품 판매 조건이 전체(null)일 경우는 null을 리턴합니다.
   *
   * @param searchSellStatus
   * @return
   */
  private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
    return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
  }


  private BooleanExpression regDsAfter(String searchDateType) {
    LocalDateTime localDateTime = LocalDateTime.now();

    if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
      return null;
    } else if (StringUtils.equals("1d", searchDateType)) {
      localDateTime = localDateTime.minusDays(1);
    } else if (StringUtils.equals("1w", searchDateType)) {
      localDateTime = localDateTime.minusWeeks(1);
    } else if (StringUtils.equals("1m", searchDateType)) {
      localDateTime = localDateTime.minusMonths(1);
    } else if (StringUtils.equals("6m", searchDateType)) {
      localDateTime = localDateTime.minusMonths(6);
    }
    return QItem.item.createdAt.after(localDateTime);
  }

  private BooleanExpression searchByLike(String searchBy, String searchQuery) {
    // searchBy 값에 따라 (where 조건으로) 상품을 조회 하도록 조건값을 반환함
    if (StringUtils.equals("itemNm", searchBy)) {
      return QItem.item.itemNm.like("%" + searchQuery + "%");
    } else if (StringUtils.equals("createdBy", searchBy)) {
      return QItem.item.createdBy.like("%" + searchQuery + "%");
    }
    return null;
  }


}
