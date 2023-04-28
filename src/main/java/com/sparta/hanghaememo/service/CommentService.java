package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.Exception.CustomException;
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
    public InterfaceDto createComment(Long id, CommentRequestDto requestDto, HttpServletRequest request){
        //hear 토큰 가져오기
        String token = jwtUtil.resolveToken(request);

        //memo저장한 db에서 id로 해당 게시물 찾기
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시물")
        );

        // 유저가 가져온 토큰 승인된 토큰인지 확인,유저 db에서 username으로 존재하는 유저인지 확인
        User user = getUserByToken(token);


        if(user != null){
          Comment comment = new Comment(requestDto);

          comment.setMemo(memo);
          comment.setUser(user);

          commentRepository.save(comment);


            return new CommentResponseDto(comment);
        }else{
            return new StatusResponseDto("사용할 수 없는 토큰입니다.", HttpStatus.BAD_REQUEST);
        }
    }
    @Transactional
    public InterfaceDto updateComment(Long id,CommentRequestDto requestDto,HttpServletRequest request){
        String token = jwtUtil.resolveToken(request);

//        Claims claims = checkingToken(request);
        User user = getUserByToken(token);

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("댓글이 없습니다")


        );
        if(comment.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        }
        else{
            return new StatusResponseDto("작성자와 관리자만 수정가능",HttpStatus.BAD_REQUEST);
        }



    }

    public StatusResponseDto deleteComment(Long id,HttpServletRequest request){
//        Claims claims =checkingToken(request);
        String token = jwtUtil.resolveToken(request);
        User user = getUserByToken(token);

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글")
        );
        if(comment.getUser().getUsername().equals(user.getUsername()) || user.getRole() == UserRoleEnum.ADMIN){
            commentRepository.delete(comment);
            return new StatusResponseDto("삭제 완료",HttpStatus.OK);
        }
        else{
            return new StatusResponseDto("작성자,관리자만 삭제가능",HttpStatus.BAD_REQUEST);
        }

    }
















public Claims checkingToken(HttpServletRequest request)
    throws NullPointerException{
        Claims claims =jwtUtil.getUserInfoFromToken(jwtUtil.resolveToken(request));
        if(claims == null){
            throw new NullPointerException("토큰 유효하지 않다");
        }
        return claims;
}





    public User getUserByToken(String token){
        Claims claims;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new CustomException(ExceptionEnum.TOKEN_NOT_FOUND);
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 사용자입니다.")
            );
            return user;
        }
        return null;
    }
}
