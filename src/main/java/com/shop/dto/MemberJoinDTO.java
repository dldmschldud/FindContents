package com.shop.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberJoinDTO {


    @NotBlank(message = "이름을 필수로 입력해야합니다")
    private String mid;

    @NotEmpty(message = "비밀번호는 필수")
    @Length(min=8, max=16, message = "비밀번호는8-16자로 설정하세요")
    private String mpw;

    @NotEmpty
    private String email;
}
