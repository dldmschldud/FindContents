package com.shop.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Theme extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;

    @Column(length = 50, nullable = false)
    private String thTitle;

    @Column(length = 2000, nullable = false)
    private String thExplanation;

    @Column(length = 50, nullable = false)
    private String thWriter;

    public void change(String thTitle, String thExplanation){
        this.thExplanation = thExplanation;
        this.thTitle = thTitle;
    }

}
