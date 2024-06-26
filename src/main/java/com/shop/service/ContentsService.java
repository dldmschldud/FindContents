package com.shop.service;

import com.shop.domain.Contents;
import com.shop.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public interface ContentsService {

    Long register(ContentsDTO ContentsDTO);
    ContentsDTO readOne(Long id);
    void modify(ContentsDTO contentDTO);
    void remove(Long id);

    PageResponseDTO<ContentsDTO> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<ContentsListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ContentsListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);
    PageResponseDTO<ContentsListAllDTO> genreList(String genre, PageRequestDTO pageRequestDTO);
    PageResponseDTO<ContentsListAllDTO> movieList(PageRequestDTO pageRequestDTO);
    PageResponseDTO<ContentsListAllDTO> dramaList(PageRequestDTO pageRequestDTO);

    PageResponseDTO<ContentsOttDTO> listWithOtt(PageRequestDTO pageRequestDTO);

    default Contents dtoToEntity(ContentsDTO contentsDTO) {
        Contents contents = Contents.builder()
                .id(contentsDTO.getId())
                .title(contentsDTO.getTitle())
                .explanation(contentsDTO.getExplanation())
                .writer(contentsDTO.getWriter())
                .category(contentsDTO.getCategory())
                .cType(contentsDTO.getCType())
                .netflix(contentsDTO.getNetflix())
                .disney(contentsDTO.getDisney())
                .watcha(contentsDTO.getWatcha())
                .build();

        return contents;
    }

    default ContentsDTO entityToDTO(Contents contents) {
        ContentsDTO contentsDTO = ContentsDTO.builder()
                .id(contents.getId())
                .title(contents.getTitle())
                .explanation(contents.getExplanation())
                .writer(contents.getWriter())
                .regDate(contents.getRegDate())
                .modDate(contents.getModDate())
                .category(contents.getCategory())
                .cType(contents.getCType())
                .disney(contents.getDisney())
                .netflix(contents.getNetflix())
                .watcha(contents.getWatcha())
                .build();

       return contentsDTO;
    }

}
