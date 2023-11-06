package com.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsListAllDTO {

    private Long id;
    private String title;
    private String writer;
    private LocalDateTime regDate;
    private Long replyCount;
    private String category;
    private String cType;
    private List<ContentsImageDTO> contentsImages;
}
