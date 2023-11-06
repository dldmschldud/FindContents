package com.shop.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude="contents")
public class ContentsImage implements Comparable<ContentsImage> {
    @Id
    private String uuid;

    private String fileName;

    private int ord;

    @ManyToOne
    private Contents contents;

    @Override
    public int compareTo(ContentsImage other) {
        return this.ord - other.ord;
    }

    public void changeContents(Contents contents) {
        this.contents = contents;
    }
}
