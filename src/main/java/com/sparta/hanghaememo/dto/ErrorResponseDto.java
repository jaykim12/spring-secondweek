package com.sparta.hanghaememo.dto;


import com.sparta.hanghaememo.Exception.ExceptionEnum;
import lombok.Getter;
@Getter
public class ErrorResponseDto {
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public ErrorResponseDto(ExceptionEnum errorCode) {
        this.status = errorCode.getStatus().value();
        this.error = errorCode.getStatus().name();
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
}