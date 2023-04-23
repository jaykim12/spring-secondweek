package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.repository.MemoRepository;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;


    //메모 생성
    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);
        memoRepository.save(memo);
        return new MemoResponseDto(memo);
    }


    //메모 전체 조회
    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemos() {
        List<Memo> lists = memoRepository.findAllByOrderByModifiedAtDesc();

        List<MemoResponseDto> memos = new ArrayList();

        for(Memo memo : lists){
            memos.add(new MemoResponseDto(memo));
        }

        return memos;
    }


    //메모 수정
    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

//        if(유효한 토큰일 시 && 해당 사용자가 작성한 게시글일 시){
//            memo.update(requestDto);
//        }else {
//            MessageService.updateMessage(isEquals);
//        }
        return new MemoResponseDto(memo);
    }


    //메모 삭제
    @Transactional
    public StatusResponseDto deleteMemo(Long id) {
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

//        if(유효한 토큰일 시 && 해당 사용자가 작성한 게시글일 시){
//            memoRepository.deleteById(id);
//        }
        return new StatusResponseDto("삭제를 성공적으로 마쳤습니다.", HttpStatus.OK);
    }


    //메모 상세 조회
    public MemoResponseDto getMemo(Long id) {
        List<Memo> lists = memoRepository.findAllByOrderByModifiedAtDesc();

        List<MemoResponseDto> memos = new ArrayList();
        MemoResponseDto selectedMemo = null;

        for (Memo memo : lists) {
            if (memo.getId() == id) {
                selectedMemo = new MemoResponseDto(memo);
            }

            if(selectedMemo == null){
                MessageService.getMemo();
            }

        }
        return selectedMemo;
    }
}
