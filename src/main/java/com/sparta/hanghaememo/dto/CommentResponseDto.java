package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto implements InterfaceDto {

    private String comment;
    private LocalDate modifiedAt;

    public CommentResponseDto(Comment comment){
        this.comment = comment.getContent();
        this.modifiedAt = comment.getModifiedAt().toLocalDate();
    }

}
