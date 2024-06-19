package com.shop.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class Contents extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contents_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(length = 500, nullable = false)
    private String explanation;

    @Column(length = 50, nullable = false)
    private String writer;

    @Column(length = 50, nullable = false)
    private String category;

    @Column(length = 50, nullable = false)
    private String cType;

    @Column(length = 50, nullable = false)
    private String netflix;

    @Column(length = 50, nullable = false)
    private String disney;

    @Column(length = 50, nullable = false)
    private String watcha;


    public void change(String title, String explanation, String writer, String category, String cType, String netflix, String watcha, String disney){
        this.title = title;
        this.explanation = explanation;
        this.writer = writer;
        this.category = category;
        this.cType = cType;
        this.netflix = netflix;
        this.watcha = watcha;
        this.disney = disney;
    }

}
