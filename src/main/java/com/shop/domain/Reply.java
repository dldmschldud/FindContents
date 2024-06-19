package com.shop.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "contents")
@Table(name = "Reply", indexes = {@Index(name="idx_reply_contents_contents_id",columnList="contents_contents_id")})
public class Reply extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contents contents;

    @NotEmpty
    private String replyText;

    @NotEmpty
    private String replyer;

    public void changeText(String text){

        this.replyText = text;
    }

    public void setContents(Contents contents){
        this.contents = contents;
    }

}
