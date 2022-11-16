package com.ex.shop.domain.member.controller;

import com.ex.shop.common.auth.principal.PrincipalDetails;
import com.ex.shop.domain.member.dto.MemberDto;
import com.ex.shop.domain.member.entity.Member;
import com.ex.shop.domain.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberService memberService;
  private final PasswordEncoder passwordEncoder;

  @GetMapping("/login")
  public String login(){
    log.info("로그인 페이지");
    return "/member/memberLoginForm";
  }

  @GetMapping("/auth/invalid")
  public String invalid(){
    return "redirect:/members/login";
  }

  @RequestMapping("/login/error")
  @ResponseBody
  public ResponseEntity<?> loginError(HttpServletRequest request){

    Throwable ex = (Throwable) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    String resultCode = "";
    if(ex != null){
      if(ex instanceof BadCredentialsException){
        log.info("비밀번호 인증 실패");
        resultCode = "01";
      }else if(ex instanceof UsernameNotFoundException) {
        log.info("존재하지 않은 회원");
        resultCode = "02";
      }else{
        log.info("그외의 에러 발생");
        resultCode = "03";
      }
    }
    return ResponseEntity.badRequest().body(resultCode);
  }

  @RequestMapping("/login/success")
  @ResponseBody
  public ResponseEntity<?> loginSuccess(
    HttpServletRequest request,
    @AuthenticationPrincipal PrincipalDetails principalDetails
  ){
    if(principalDetails != null){
      log.info("유저 정보 : {} ", principalDetails.getMember().toString());
    }
    return ResponseEntity.ok().body("00");
  }

  @GetMapping("/new")
  public String memberForm(Model model){
    model.addAttribute("memberFormDto",new MemberDto());
    return "member/memberForm";
  }

  @PostMapping("/new")
  public String memberForm(@Valid  MemberDto memberDto, BindingResult bindingResult, Model model){

    if(bindingResult.hasErrors()){
      return "member/memberForm";
    }
    try {
      memberService.saveMember(Member.of(memberDto, passwordEncoder));
    } catch (IllegalStateException e) {
      model.addAttribute("errorMessage",e.getMessage());
      return "member/memberForm";
    }
    return "redirect:/";
  }
}
















