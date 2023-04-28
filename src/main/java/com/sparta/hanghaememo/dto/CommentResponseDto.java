package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {
    private Long id;
    private String username;
    private String comment;
    private LocalDate modifiedAt;

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getContent();
        this.modifiedAt = comment.getModifiedAt().toLocalDate();
    }
}
