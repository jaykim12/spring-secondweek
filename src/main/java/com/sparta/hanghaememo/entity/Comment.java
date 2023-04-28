package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "memo_id")
    private Memo memo;

    public  void setMemo(Memo memo){
        this.memo = memo;
        memo.getComments().add(this);
    }


    public void setUser(User user){
        this.user = user;
    }

    public Comment(CommentRequestDto requestDto){
        this.content = requestDto.getComment();
    }

    public void update(CommentRequestDto requestDto) {
//        this.username = requestDto.getUsername();
        this.content = requestDto.getComment();

    }
}
