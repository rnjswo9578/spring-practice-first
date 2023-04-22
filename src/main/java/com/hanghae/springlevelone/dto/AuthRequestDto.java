package com.hanghae.springlevelone.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthRequestDto {

    @Size(min = 4, max = 10, message = "아이디는 4~10자리이어야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 영어 소문자와 숫자만 가능합니다.")
    private String username;

    @Size(min = 8, max = 15, message = "비밀번호는 8~15자리이어야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "비밀번호는 대소문자, 숫자로만 가능합니다.")
    private String password;
}
