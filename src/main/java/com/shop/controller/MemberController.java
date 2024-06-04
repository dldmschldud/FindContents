package com.shop.controller;

import com.shop.dto.MemberJoinDTO;
import com.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGETS() {

    }

    @GetMapping("/login/error")
    public String loginError(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("errors","아이디또는 비밀번호를 확인하세요");
        return "redirect:/member/login";
    }

    @GetMapping("/join")
    public void joinGET(){

        log.info("join get........");
    }


    @PostMapping("/join")
    public String joinPOST(@Valid MemberJoinDTO memberJoinDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("join post......");
        log.info("checkkkkkkkkkkkkkk"+memberJoinDTO);

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            redirectAttributes.addFlashAttribute("errors",errors);
            return "redirect:/member/join";
        }

        try{
            memberService.join(memberJoinDTO);
        } catch(MemberService.MidExistException e){
            redirectAttributes.addFlashAttribute("error","mid");
            return "redirect:/member/join";
        }
        redirectAttributes.addFlashAttribute("result", "success");

        return "redirect:/contents/list";
    }
}
