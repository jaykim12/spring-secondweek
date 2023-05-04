package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.Exception.CustomException;
import com.sparta.hanghaememo.Exception.ExceptionEnum;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.entity.Comment;
import com.sparta.hanghaememo.entity.CommentLike;
import com.sparta.hanghaememo.entity.MemoLike;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.repository.CommentLikeRepository;
import com.sparta.hanghaememo.repository.CommentRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public StatusResponseDto push(Long id, UserDetailsImpl userDetails){

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.COMMENTS_NOT_FOUND)
        );
        User user = userDetails.getUser();

        Optional<CommentLike> found = commentLikeRepository.findByUserAndComment(user,comment);

        if(found.isEmpty()){
            CommentLike commentLike = new CommentLike(comment,user);
            commentLikeRepository.save(commentLike);
            comment.plusLike();
            return new StatusResponseDto("LIKE", HttpStatus.OK, comment.getLikes());


        }
        else{
           commentLikeRepository.delete(found.get());
           comment.minusLike();
            return new StatusResponseDto("LIKE 취소",HttpStatus.OK, comment.getLikes());

        }
    }


}
