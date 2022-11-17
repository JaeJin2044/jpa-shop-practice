package com.ex.shop.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.asm.Advice.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EntityListeners(value = {AuditingEntityListener.class}) //Auditing을 적용하기 위해 어노테이션 추가
@MappedSuperclass // 공통 매핑정보가 필요할때 사용하는 어노테이션 -> 상속받는 자식 클래스에게 매핑 정보만 제공한다.
@Setter
@Getter
public class BaseTimeEntity {

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;


}
