package com.sparta.hanghaememo.controller;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.service.MemoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    //ModelAndView에 데이터와 뷰의 정보 저장
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }


    //메모 생성
    @PostMapping("/api/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.createMemo(requestDto, request);
    }


    //메모 전체 조회
    @GetMapping("/api/memos")
    public List<MemoResponseDto> getMemos(){
        return memoService.getMemos();
    }


    //메모 상세 조회
    @GetMapping("/api/memos/{id}")
    public MemoResponseDto getMemo(@PathVariable Long id){
        return memoService.getMemo(id);
    }


    //메모 수정
    @PutMapping("/api/memos/{id}")
    public MemoResponseDto updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto, HttpServletRequest request){
        return memoService.update(id, requestDto, request);
    }


    //메모 삭제
    @DeleteMapping("/api/memos/{id}")
    public StatusResponseDto deleteMemo(@PathVariable Long id, HttpServletRequest request){
        return memoService.deleteMemo(id, request);
    }
}
