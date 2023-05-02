package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.Exception.CustomException;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import com.sparta.hanghaememo.dto.MemoResponseDto;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.Exception.ExceptionEnum;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.entity.UserRoleEnum;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //메모 생성
    @Transactional
    public MemoResponseDto createMemo(MemoRequestDto requestDto, User user) {
//        String token = jwtUtil.resolveToken(request);
//
//        User user = getUserByToken(token);
        Memo memo = new Memo(requestDto,user);
        
        memoRepository.save(memo);
        return new MemoResponseDto(memo);
    }


    //메모 전체 조회
    @Transactional(readOnly = true)
    public List<MemoResponseDto> getMemos() {
        return memoRepository.findAll().stream().map(MemoResponseDto::new).collect(Collectors.toList());
    }


    //메모 수정
    @Transactional
    public MemoResponseDto update(Long id, MemoRequestDto requestDto, User user) {
//        String token = checkToken(request);
//        User user = getUserByToken(token);

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.USER_NOT_FOUND)
        );

        if(memo.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN ){
            memo.update(requestDto);
            return new MemoResponseDto(memo);
        }

        throw new CustomException(ExceptionEnum.NOT_ALLOW_AUTHORIZATIONS);
    }



    //메모 삭제
    @Transactional
    public StatusResponseDto deleteMemo(Long id, User user) {
//        String token = checkToken(request);
//        User user = getUserByToken(token);

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        if(user.getUsername().equals(memo.getUser().getUsername())|| user.getRole() == UserRoleEnum.ADMIN ){
            memoRepository.deleteById(memo.getId());
            return new StatusResponseDto("삭제를 성공적으로 마쳤습니다.", HttpStatus.OK);
        }

        throw new CustomException(ExceptionEnum.NOT_ALLOW_AUTHORIZATIONS);
    }


    //메모 상세 조회
    public MemoResponseDto getMemo(Long id) {
        return new MemoResponseDto(memoRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.MEMO_NOT_FOUND)
        ));
    }


    //
//    public User getUserByToken(String token){
//        Claims claims;
//
//        if(token != null){
//            if(jwtUtil.validateToken(token)){
//                claims = jwtUtil.getUserInfoFromToken(token);
//            }else{
//                throw new CustomException(ExceptionEnum.TOKEN_NOT_FOUND);
//            }
//
//            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
//                    () -> new CustomException(ExceptionEnum.USER_NOT_FOUND)
//            );
//            return user;
//        }
//        throw new CustomException(ExceptionEnum.TOKEN_NOT_FOUND);
//    }
//
//    private String checkToken(HttpServletRequest request) {
//        String token = jwtUtil.resolveToken(request);
//        if (!jwtUtil.validateToken(token)) {
//            throw new CustomException(ExceptionEnum.TOKEN_NOT_FOUND);
//        }
//        return token;
//    }
}
