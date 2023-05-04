package com.sparta.hanghaememo.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class StatusResponseDto<T> {
    private String msg;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> StatusResponseDto<T> statusResponseDto(String msg, HttpStatus status, T data){
        return new StatusResponseDto<>(msg, status, data);
    }
}