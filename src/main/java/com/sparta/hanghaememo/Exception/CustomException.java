package com.sparta.hanghaememo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ExceptionEnum errorCode;
}
