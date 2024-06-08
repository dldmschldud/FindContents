package com.shop.controller;

import com.shop.dto.*;
import com.shop.service.ContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentsController {

    @Value("${org.zerock.upload.path")
    private String uploadPath;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/register")
    public void registerGET() {
        log.info("-------------------registerGET---------------------");

    }

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
    @GetMapping({"/read", "/modify"})
    public void read(Long id, PageRequestDTO pageRequestDTO, Model model) {
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

        //게시물이 삭제되었다면 첨부 파일 삭제
        log.info(contentsDTO.getFileNames());
        List<String> fileNames = contentsDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0){
            removeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/contents/list";

    }


    public void removeFiles(List<String> files){

        for (String fileName:files) {

            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();


            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                //썸네일이 존재한다면
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumbnailFile.delete();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }

}
