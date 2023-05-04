package com.sparta.hanghaememo.dto;

import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.Memo;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemoResponseDto {

    private String createdAt;
    private String modifiedAt;
    private Long id;
    private String title;
    private String content;
    private String username;
    private List<CommentResponseDto> commentResponseDtos;
    private Integer likes;

    public MemoResponseDto(Memo memo) {
        this.createdAt = String.valueOf(memo.getCreatedAt());
        this.modifiedAt = String.valueOf(memo.getModifiedAt());
        this.id = memo.getId();
        this.title = memo.getTitle();
        this.content = memo.getContents();
        this.username = memo.getUser().getUsername();
        this.commentResponseDtos = memo.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
        this.likes = memo.getLikes();
    }
}
