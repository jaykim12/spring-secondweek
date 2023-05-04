package com.sparta.hanghaememo.repository;

import com.sparta.hanghaememo.entity.Memo;
import com.sparta.hanghaememo.entity.MemoLike;
import com.sparta.hanghaememo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemoLikeRepository extends JpaRepository<MemoLike,Long> {

    Optional<MemoLike> findByUserAndMemo(User user, Memo memo);

}
