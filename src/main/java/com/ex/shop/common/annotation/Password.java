package com.ex.shop.common.annotation;

import com.ex.shop.common.util.PasswordValidate;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import org.apache.commons.lang3.StringUtils;


@Constraint(validatedBy = Password.PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

  String message() default "비밀번호를 올바르게 입력해주세요";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 6;

  int max() default 100;

  boolean nullable() default false;

  class PasswordValidator implements ConstraintValidator<Password, String> {

    private int min;
    private int max;
    private boolean nullable;

    // 어노테이션 등록시 입력했던 parameter초기화
    @Override
    public void initialize(Password passwordValidator) {
      min = passwordValidator.min();
      max = passwordValidator.max();
      nullable = passwordValidator.nullable();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
      return isPasswordValidation(password, context);

    }

    private boolean isPasswordValidation(String password, ConstraintValidatorContext context) {

      //null여부 체크
      if (!nullable && StringUtils.isBlank(password)) {
        addConstraintViolation(context, "비밀번호를 입력해주세요");
        return false;
      } else if (nullable) {
        return true;
      }

      //정규식 패턴 체크
      PasswordValidate passwordValidate = new PasswordValidate(password)
        .minLength(min)
        .maxLength(max)
        .combineHangeul()
        .combineDigitAndWordAndSpecial();

      switch (passwordValidate.validate()) {
        case minWordLength:
        case maxWordLength:
        case upperLower:
        case notDigitAndWordAndSpecial:
        case hangeul:
          addConstraintViolation(context,
            String.format("비밀번호는 영문(대소문자 구분)+숫자+특수문자 포함 %d ~ %d자로 입력해주세요", min, max));
          return false;
      }
      return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String msg) {
      //기본 메시지 비활성화
      context.disableDefaultConstraintViolation();
      //새로운 메시지 추가
      context.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
    }
  }

}
