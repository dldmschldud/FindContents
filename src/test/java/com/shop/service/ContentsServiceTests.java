package com.shop.service;

import com.shop.dto.*;
import com.shop.repository.ContentsRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ContentsServiceTests {

    @Autowired private ContentsService contentsService;

    @Test
    public void testRegister() {
        for(int i=0;i<15;i++){
            ContentsDTO contentsDTO = ContentsDTO.builder()
                    .title("title..")
                    .explanation("explanation")
                    .writer("writer"+String.valueOf(i%10))
                    .category("action")
                    .cType("drama")
                    .build();
            contentsService.register(contentsDTO);
        }

    }

    @Test
    public void testModify() {
        ContentsDTO contentsDTO = ContentsDTO.builder()
                .id(101L)
                .title("updated 101L")
                .explanation("updated 101L")
                .writer("updated 101L")
                .category("action")
                .build();

        contentsDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));

        contentsService.modify(contentsDTO);
    }
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("te")
                .keyword("1")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<ContentsDTO> responseDTO = contentsService.list(pageRequestDTO);
        log.info(responseDTO);
    }
    @Test
    public void testListWithAll() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<ContentsListAllDTO> responseDTO = contentsService.listWithAll(pageRequestDTO);
        List<ContentsListAllDTO> dtoList = responseDTO.getDtoList();
        dtoList.forEach(contentsListAllDTO -> {
            log.info(contentsListAllDTO.getId()+":"+contentsListAllDTO.getTitle());
            if(contentsListAllDTO.getContentsImages() != null) {
                for(ContentsImageDTO contentsImage : contentsListAllDTO.getContentsImages()) {

                    log.info(contentsImage);
                }
            }
            log.info("_________________________");
        });

    }

    @Test
    public void testRegisterWithImages() {
        log.info(contentsService.getClass().getName());

        ContentsDTO contentsDTO = ContentsDTO.builder()
                .title("File sample title")
                .explanation("sample explanation")
                .writer("user")
                .build();

        contentsDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_ccc.jpg"

                        )
        );

        Long id = contentsService.register(contentsDTO);
        log.info("id: ="+id);
    }

    @Test
    public void testReadAll() {
        Long id = 101L;
        ContentsDTO contentsDTO = contentsService.readOne(id);
        log.info(contentsDTO);

        for (String fileName : contentsDTO.getFileNames()) {
            log.info(fileName);
        }
    }
    @Test
    public void testRemoveAll() {
        Long id = 1L;
        contentsService.remove(id);
    }
}
