package com.ex.shop.domain.item.service;

import com.ex.shop.domain.global.dto.MainItemDto;
import com.ex.shop.domain.item.dto.ItemFormDto;
import com.ex.shop.domain.item.dto.ItemImgDto;
import com.ex.shop.domain.item.dto.ItemSearchDto;
import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.entity.ItemImg;
import com.ex.shop.domain.item.repository.ItemImgRepository;
import com.ex.shop.domain.item.repository.ItemRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final ItemImgService itemImgService;
  private final ItemImgRepository itemImgRepository;

  @Transactional
  public Long saveItem(ItemFormDto itemFormDto,
    List<MultipartFile> itemImgFileList) throws Exception{

    //상품 등록
    Item item = itemFormDto.createItem();
    itemRepository.save(item);

    //이미지 등록
    int i = 0;
    for (MultipartFile file : itemImgFileList) {
      ItemImg itemImg = new ItemImg();
      itemImg.setItem(item);
      itemImg.setReimgYn( i == 0 ? "Y" : "N");
      itemImgService.saveItemImg(itemImg,file);
      i++;
    }
    return item.getId();
  }

  @Transactional
  public Long updateItem(ItemFormDto itemFormDto,
    List<MultipartFile> itemImgFileList) throws Exception{

    //상품 수정
    Item item = itemRepository.findById(itemFormDto.getId())
      .orElseThrow(EntityNotFoundException::new);

    //영속 상태이므로 변환시 자동 update
    item.updateItem(itemFormDto);

    List<Long> itemImgIds = itemFormDto.getItemImgIds();

    //이미지 등록

    if(itemImgFileList != null){

      int i = 0;
      for (MultipartFile file : itemImgFileList) {
        itemImgService.updateItemImg(itemImgIds.get(i), file);
        i++;
      }

    }
    return item.getId();
  }



  @Transactional(readOnly = true) // 읽기전용 -> JPA가 더티체킹(변경감지)를 수행하지 않기 때문에 성능향상된다.
  public ItemFormDto getItemDtl(Long itemId){

    List<ItemImg> itemImgList = itemImgRepository.findByItemIdOrderByIdAsc(itemId);

    List<ItemImgDto> itemImgDtoList = itemImgList.stream()
      .map(ItemImgDto::of).collect(Collectors.toList());

    Item item = itemRepository.findById(itemId)
      .orElseThrow(EntityNotFoundException::new);

    ItemFormDto itemFormDto = ItemFormDto.of(item);
    itemFormDto.setItemImgDtoList(itemImgDtoList);

    return itemFormDto;
  }

//  @Transactional(readOnly = true) // 읽기전용 -> JPA가 더티체킹(변경감지)를 수행하지 않기 때문에 성능향상된다.
//  public ItemFormDto getItemDtl2(Long itemId){
//
//    Item item = itemRepository.findById(itemId)
//      .orElseThrow(EntityNotFoundException::new);
//
//    List<ItemImg> itemImgList = item.getItemImgList();
//
//    List<ItemImgDto> itemImgDtoList = itemImgList.stream()
//      .map(ItemImgDto::of).collect(Collectors.toList());
//
//    ItemFormDto itemFormDto = ItemFormDto.of(item);
//    itemFormDto.setItemImgDtoList(itemImgDtoList);
//
//    return itemFormDto;
//  }


  @Transactional(readOnly = true)
  public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
//    Page<Item> adminItemPage = itemRepository.getAdminItemPage(itemSearchDto, pageable);
//
//    List<Item> content = adminItemPage.getContent();
//
//    content.forEach(o -> {
//      List<ItemImg> itemImgList = o.getItemImgList();
//      System.out.println(itemImgList);
//    });

    return itemRepository.getAdminItemPage(itemSearchDto,pageable);
  }

  // 메인 페이지에 보여줄 상품 데이테 조회
  @Transactional(readOnly = true)
  public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
    return itemRepository.getMainItemPage(itemSearchDto, pageable);
  }
}
