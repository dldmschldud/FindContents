package com.shop.dto;

import groovy.lang.DelegatesTo;
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
public class ContentsOttDTO {

    private Long id;
    private String title;
    private String netflix;
    private String watcha;
    private String disney;


}
