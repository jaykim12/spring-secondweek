package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.Exception.CustomException;
import com.sparta.hanghaememo.Exception.ExceptionEnum;
import com.sparta.hanghaememo.dto.*;
import com.sparta.hanghaememo.entity.*;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemoRepository memoRepository;
    private  final UserRepository userRepository;
    private  final JwtUtil jwtUtil;


    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest request){
        //hear 토큰 가져오기
        String token = jwtUtil.resolveToken(request);

        //memo저장한 db에서 id로 해당 게시물 찾기
        Memo memo = memoRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new CustomException(ExceptionEnum.MEMO_NOT_FOUND)
        );

        // 유저가 가져온 토큰 승인된 토큰인지 확인,유저 db에서 username으로 존재하는 유저인지 확인
        User user = getUserByToken(token);

        Comment comment = new Comment(requestDto);
        comment.setMemo(memo);
        comment.setUser(user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long id,CommentRequestDto requestDto,HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);
        User user = getUserByToken(token);

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.COMMENTS_NOT_FOUND)
        );

        if(comment.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        }

        throw new CustomException(ExceptionEnum.NOT_ALLOW_AUTHORIZATIONS);
    }

    @Transactional
    public StatusResponseDto deleteComment(Long id,HttpServletRequest request){
//        Claims claims =checkingToken(request);
        String token = jwtUtil.resolveToken(request);
        User user = getUserByToken(token);

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.COMMENTS_NOT_FOUND)
        );

        if(comment.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            commentRepository.delete(comment);
            return new StatusResponseDto("삭제 완료",HttpStatus.OK);
        }

        throw new CustomException(ExceptionEnum.NOT_ALLOW_AUTHORIZATIONS);
    }







    public User getUserByToken(String token){
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new CustomException(ExceptionEnum.TOKEN_NOT_FOUND);
            }

            return userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new CustomException(ExceptionEnum.USER_NOT_FOUND)
            );
        }
        throw new CustomException(ExceptionEnum.TOKEN_NOT_FOUND);
    }
}
