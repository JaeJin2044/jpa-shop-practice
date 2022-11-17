package com.ex.shop.domain.item.service;

import com.ex.shop.common.service.FileService;
import com.ex.shop.domain.item.entity.ItemImg;
import com.ex.shop.domain.item.repository.ItemImgRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemImgService {

  @Value("${itemImgLocation}")
  private String itemImgLocation;

  private final ItemImgRepository itemImgRepository;
  private final FileService fileService;

  @Transactional
  public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception{
    String oriImgName = itemImgFile.getOriginalFilename();
    String imgName = "";
    String imgUrl = "";

    //파일 업로드
    if(StringUtils.isNotEmpty(oriImgName)){
      imgName = fileService.uploadFile(itemImgLocation,oriImgName,itemImgFile.getBytes());
      imgUrl = "/images/item/"+imgName;
    }

    //상품 이미지 정보 저장
    itemImg.updateItemImg(oriImgName,imgName,imgUrl);
    itemImgRepository.save(itemImg);
  }

  @Transactional
  public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception{

    if(!itemImgFile.isEmpty()){
      ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
        .orElseThrow(EntityNotFoundException::new);

      //기존 이미지 파일 삭제
      if(StringUtils.isNotEmpty(savedItemImg.getImgName())){
        fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName());
      }

      String oriImgName = itemImgFile.getOriginalFilename();
      String imgName = fileService.uploadFile(itemImgLocation,oriImgName, itemImgFile.getBytes());
      String imgUrl = "/images/item/"+imgName;
      // save()호출을 안했는데 어떻게 디비 반영?
      // savedItemImg는 이미 조회를 한번 한 상태라 현재 영속 상태이다.
      // 영속 상태에서는 데이터를 변경하는것만으로 트랜잭션이 끝날때  UPDATE 쿼리가 날아간다.
      savedItemImg.updateItemImg(oriImgName,imgName,imgUrl);
    }
  }



}
