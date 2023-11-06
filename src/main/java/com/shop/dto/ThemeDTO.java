package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDTO {

    private Long tid;

    @NotEmpty
    @Size(min = 1, max = 100)
    private String thTitle;

    @NotEmpty
    private String thExplanation;

    @NotEmpty
    private String thWriter;


}
