package com.shop.controller;

import com.shop.domain.Theme;
import com.shop.dto.PageRequestDTO;
import com.shop.dto.PageResponseDTO;
import com.shop.dto.ThemeDTO;
import com.shop.repository.ThemeRepository;
import com.shop.repository.search.ThemeSearch;
import com.shop.service.ThemeService;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.spring5.expression.Themes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/themes")
@Log4j2
@RequiredArgsConstructor
public class ThemeController {

    private final ThemeService themeService;
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
       PageResponseDTO<ThemeDTO> responseDTO = themeService.list(pageRequestDTO);
       model.addAttribute("responseDTO",responseDTO);
       log.info(responseDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/theme","/modify"})
    public void theme(Long tid,PageRequestDTO pageRequestDTO, Model model){
        ThemeDTO themeDTO = themeService.readTheme(tid);
        log.info(themeDTO);
        model.addAttribute("dto",themeDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/write")
    public void writeGET(){
        log.info("write get theme");
    }

    @PostMapping("/write")
    public String writePost(@Valid ThemeDTO themeDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("post write theme");
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            return "redirect:/themes/write";
        }
        Long tid = themeService.register(themeDTO);
        redirectAttributes.addFlashAttribute("result",tid);
        return "redirect:/themes/list";
    }

    @PreAuthorize("principal.username == #themeDTO.thWriter")
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid ThemeDTO themeDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("BBB"+themeDTO);
        if(bindingResult.hasErrors()){
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors",bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tid",themeDTO.getTid());
            return "redirect:/themes/modify?"+link;
        }
        themeService.modify(themeDTO);
        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("tid",themeDTO.getTid());
        return "redirect:/themes/theme";
    }

    @PreAuthorize("principal.username == #themeDTO.thWriter")
    @PostMapping("/remove")
    public String remove(ThemeDTO themeDTO, RedirectAttributes redirectAttributes){
        Long tid = themeDTO.getTid();
        log.info("post remove");
        themeService.remove(tid);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/themes/list";
    }


}
