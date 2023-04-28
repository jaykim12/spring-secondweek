package com.sparta.hanghaememo.Exception;

import com.sparta.hanghaememo.entity.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    private final ExceptionEnum errorCode;
}
