package com.ex.shop.domain.item.controller;

import com.ex.shop.domain.item.dto.ItemFormDto;
import com.ex.shop.domain.item.dto.ItemSearchDto;
import com.ex.shop.domain.item.entity.Item;
import com.ex.shop.domain.item.service.ItemService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

  private final ItemService itemService;

  @GetMapping("/sample")
  @ResponseBody
  public String smaple(){
    log.info("SAMPLE()");
    return "sample";
  }


  // 상품 관리 화면 get 페이지
  @GetMapping(value = {"/admin/items", "/admin/items/{page}"}) // url 에 페이지 번호가 없는거랑, 페이지 번호가 있는거 둘 다 매핑해줌
  public String itemManage(ItemSearchDto itemSearchDto, @PathVariable("page") Optional<Integer> page, Model model){

    // 한 페이지 당 3개
    Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
    // 0: 조회할 페이지 번호, 3: 한 번에 가지고 올 데이터 수
    // url 에 페이지 번호가 있으면은 그 페이지를 보여주고, url 에 번호가 없으면 0 페이지 보여줌

    Page<Item> items = itemService.getAdminItemPage(itemSearchDto, pageable); // itemSearchDto: 조회 조건 pageable: 페이징 정보

    model.addAttribute("items", items); // item: 조회한 상품 데이터
    model.addAttribute("itemSearchDto", itemSearchDto); // 페이지 전환 시 기존 검색 조건을 유지한 채 이동할 수 있게 뷰에 전달
    model.addAttribute("maxPage", 5); // 최대 5개의 이동할 페이지 번호를 보여줌줌
    return "item/itemMng"; // 조회한 상품 데이터 전달받는 페이지
  }




  @GetMapping("/admin/item/new")
  public String itemForm(Model model){
    model.addAttribute("itemFormDto",new ItemFormDto());
    return "item/itemForm";
  }

  @PostMapping("/admin/item/new")
  public String itemNew(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
    Model model, @RequestParam("itemImgFile")List<MultipartFile> itemImgFileList){

    if(bindingResult.hasErrors()){
      return "item/itemForm";
    }

    if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
      model.addAttribute("errorMessage","첫번째 상품 이미지는 필수 입니다.");
      return "item/itemForm";
    }

    try {
      itemService.saveItem(itemFormDto,itemImgFileList);
    } catch (Exception e) {
      model.addAttribute("errorMessage","상품 등록 중 에러가 발생하였습니다.");
      return "item/itemForm";
    }
    return "redirect:/";
  }

  @PostMapping(value = "/admin/item/{itemId}")
  public String itemUpdate(@Valid ItemFormDto itemFormDto, BindingResult bindingResult,
    @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, Model model){

    if(bindingResult.hasErrors()){
      return "item/itemForm";
    }

    if(itemImgFileList.get(0).isEmpty() && itemFormDto.getId() == null){
      model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
      return "item/itemForm";
    }

    try {
      itemService.updateItem(itemFormDto, itemImgFileList);
    } catch (Exception e){
      log.info("",e);
      model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
      return "item/itemForm";
    }
    return "redirect:/";
  }



  @GetMapping("/admin/item/{itemId}")
  public String itemDtl(@PathVariable("itemId") Long itemId, Model model){
    try {
      ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
      model.addAttribute("itemFormDto", itemFormDto);
    } catch (Exception e) {
      model.addAttribute("errorMessage","존재하지 않는 상품입니다.");
      model.addAttribute("itemFormDto", new ItemFormDto());
      return "item/itemForm";
    }
    return "item/itemForm";
  }

  // 상품 상세 get 페이지
  @GetMapping(value = "/item/{itemId}")
  public String itemDtl(Model model, @PathVariable("itemId") Long itemId) {
    // getItemDtl: service 에 있는 메소드. 상품이랑, 상품이미지의 entity -> dto 로 바꾸기만 하는 service
    ItemFormDto itemFormDto = itemService.getItemDtl(itemId);
    model.addAttribute("item", itemFormDto);
    return "item/itemDtl";
  }
}
