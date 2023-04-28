package com.sparta.hanghaememo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class StatusResponseDto {
    private String msg;
    private HttpStatus status;
}