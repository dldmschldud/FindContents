package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentsDTO {
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @NotEmpty
    private String explanation;
    @NotEmpty
    private String writer;

    @NotEmpty
    private String category;

    @NotEmpty
    private String cType;

    @NotEmpty
    private String netflix;

    @NotEmpty
    private String watcha;

    @NotEmpty
    private String disney;

    private LocalDateTime regDate;
    private LocalDateTime modDate;



}
