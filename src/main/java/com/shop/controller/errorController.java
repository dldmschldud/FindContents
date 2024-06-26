package com.shop.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
@Log4j2
@RequiredArgsConstructor
public class errorController {

    @GetMapping
    public String errorGET(){
        return "redirect:/content/list";
    }
}
