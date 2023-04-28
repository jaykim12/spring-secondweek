package com.sparta.hanghaememo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequestDto {

    @Pattern(regexp = "^[0-9a-z]{4,10}$", message = "4 ~ 10자 사이의 알파벳 소문자와 숫자만 가능합니다.")
    private String username;

    @Pattern(regexp = "^[0-9a-zA-Z]{8,15}$", message = "8 ~ 15자 사이의 알파벳 대소문자와 숫자만 가능합니다.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";

}
