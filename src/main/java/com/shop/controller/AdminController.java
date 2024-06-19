package com.shop.controller;

import com.shop.domain.Member;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public void admin(Model model){
        Pageable pageable = PageRequest.of(0,10, Sort.by("mid").descending());
        Page<Member> result = memberRepository.findAll(pageable);
        List<Member> memberList = result.getContent();
        log.info(memberList);
        model.addAttribute("memberList",memberList);
    }
}
