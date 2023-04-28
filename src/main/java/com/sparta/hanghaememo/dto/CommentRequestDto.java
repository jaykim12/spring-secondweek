package com.sparta.hanghaememo.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String comment;
}
