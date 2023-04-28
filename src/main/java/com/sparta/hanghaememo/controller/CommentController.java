package com.sparta.hanghaememo.controller;


import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.InterfaceDto;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{id}")
    public InterfaceDto createComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(id,requestDto,request);
    }

    @PutMapping("/comment/{id}")
    public InterfaceDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,HttpServletRequest request){
        return commentService.updateComment(id,requestDto,request);
    }

    @DeleteMapping("/comment/{id}")
    public StatusResponseDto deleteComment(@PathVariable Long id, HttpServletRequest request){
        return commentService.deleteComment(id,request);
    }





}
