package com.sparta.hanghaememo.service;


import com.sparta.hanghaememo.Exception.CustomException;
import com.sparta.hanghaememo.Exception.ExceptionEnum;
import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.MemoLike;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.repository.MemoLikeRepository;
import com.sparta.hanghaememo.repository.MemoRepository;
import com.sparta.hanghaememo.repository.UserRepository;
import com.sparta.hanghaememo.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoLikeService {

    private final MemoLikeRepository memoLikeRepository;
    private final UserRepository userRepository;
    private final MemoRepository memoRepository;



    @Transactional
    public StatusResponseDto push(Long id,User user){

        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionEnum.MEMO_NOT_FOUND)
        );
        Optional<MemoLike> found = memoLikeRepository.findByUserAndMemo(user,memo);



        if(found.isEmpty()){
            MemoLike memoLike = new MemoLike(user,memo);
            memoLikeRepository.save(memoLike);
            memo.plusLike();
            return new StatusResponseDto("LIKE", HttpStatus.OK, memo.getLikes());


        }
        else{
            memoLikeRepository.delete(found.get());
            memo.minusLike();
            return new StatusResponseDto("LIKE 취소",HttpStatus.OK,memo.getLikes());

        }


    }
}
