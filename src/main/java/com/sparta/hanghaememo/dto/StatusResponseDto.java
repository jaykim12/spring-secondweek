package com.sparta.hanghaememo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class StatusResponseDto {
    private String msg;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> StatusResponseDto<T> statusResponseDto(String msg, HttpStatus status, T data){
        return new StatusResponseDto<>(msg, status, data);
    }
}