package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.InterfaceDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //메모 생성
    @Transactional
    public InterfaceDto createMemo(MemoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
//        Claims claims = jwtUtil.getUserInfoFromToken(token);

        User user = getUserByToken(token);

        if(user != null){
            Memo memo = new Memo(requestDto);
            memo.setUser(user);
            memoRepository.save(memo);
            return new MemoResponseDto(memo);
        }else{
            return new StatusResponseDto("사용할 수 없는 토큰입니다.", HttpStatus.BAD_REQUEST);
        }

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
    public InterfaceDto update(Long id, MemoRequestDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        User user = getUserByToken(token);

        if(memo.getUser().getUsername().equals(user.getUsername())){
            memo.update(requestDto);
        }else{
            return new StatusResponseDto("해당 메모의 작성자만 수정이 가능합니다.", HttpStatus.BAD_REQUEST);
        }
        return new MemoResponseDto(memo);
    }


    //메모 삭제
    @Transactional
    public StatusResponseDto deleteMemo(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        User user = getUserByToken(token);

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if(user.getUsername().equals(memo.getUser().getUsername())){
            memoRepository.deleteById(memo.getId());
        }else{
            return new StatusResponseDto("해당 메모의 작성자만 메모를 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }
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


    //해당 메서드 사용해서 예외처리를 이용하면 서버 에러로 나옴 (500, Server Error)
    public User getUserByToken(String token){
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("허가되지 않은 토큰입니다.");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );
            return user;
        }
        return null;
    }
}
