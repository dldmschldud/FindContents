package com.shop.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentsListReplyCountDTO {

    private Long id;
    private String title;
    private String writer;

    private LocalDateTime regDate;
    private Long replyCount;
}
