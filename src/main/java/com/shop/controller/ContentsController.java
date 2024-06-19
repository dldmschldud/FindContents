package com.shop.controller;

import com.shop.dto.*;
import com.shop.service.ContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
@Controller
@Log4j2
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentsController {

    private final ContentsService contentsService;
    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){

        PageResponseDTO<ContentsListAllDTO> responseDTO =
                contentsService.listWithAll(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
    }
    @GetMapping("/find")
    public void find(@ModelAttribute("pp") PageRequestDTO pageRequestDTO,Model model){

        PageResponseDTO<ContentsOttDTO> responseDTO =
                contentsService.listWithOtt(pageRequestDTO);
        log.info(responseDTO.getDtoList());
        model.addAttribute("responseDTO",responseDTO);

    }
    @GetMapping("/genre")
    public void genre(String genre, PageRequestDTO pageRequestDTO, Model model){
        pageRequestDTO.setSize(5);
        PageResponseDTO<ContentsListAllDTO> responseDTO =
                contentsService.genreList(genre,pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
        model.addAttribute("genre",genre);
    }

    @GetMapping("/movie")
    public void movie(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<ContentsListAllDTO> responseDTO =
                contentsService.movieList(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
    }
    @GetMapping("/drama")
    public void drama(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<ContentsListAllDTO> responseDTO =
                contentsService.dramaList(pageRequestDTO);
        model.addAttribute("responseDTO",responseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/register")
    public void registerGET() {
        log.info("-------------------registerGET---------------------");

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public String registerPost(@Valid ContentsDTO contentsDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("contents POST register......");

        if(bindingResult.hasErrors()) {
            log.info("hasErrors.........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/contents/register";
        }
        Long id = contentsService.register(contentsDTO);
        redirectAttributes.addFlashAttribute("result",id);
        return "redirect:/contents/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/read")
    public void read(Long id, PageRequestDTO pageRequestDTO, Model model) {
        ContentsDTO contentsDTO = contentsService.readOne(id);
        model.addAttribute("dto",contentsDTO);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping({ "/modify"})
    public void modifyGet(Long id, PageRequestDTO pageRequestDTO, Model model) {
        ContentsDTO contentsDTO = contentsService.readOne(id);
        model.addAttribute("dto",contentsDTO);
    }


    @PreAuthorize("principal.username == #contentsDTO.writer")
    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid ContentsDTO contentsDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        log.info("contents modify post.......");

        if(bindingResult.hasErrors()) {
            log.info("has errors...");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("id",contentsDTO.getId());
            return "redirect:/contents/modify?"+link;
        }
        contentsService.modify(contentsDTO);

        redirectAttributes.addFlashAttribute("result","modified");
        redirectAttributes.addAttribute("id",contentsDTO.getId());//read.html로 가져가는 데이터
        return "redirect:/contents/read";
    }

    @PreAuthorize("principal.username == #contentsDTO.writer")
    @PostMapping("/remove")
    public String remove(ContentsDTO contentsDTO, RedirectAttributes redirectAttributes) {

        Long id  = contentsDTO.getId();
        log.info("remove post.. " + id);

        contentsService.remove(id);


        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/contents/list";

    }


}
