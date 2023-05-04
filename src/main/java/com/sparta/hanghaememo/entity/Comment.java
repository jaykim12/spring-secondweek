package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.CommentRequestDto;
import com.sparta.hanghaememo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
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

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likes = 0;

    @OneToMany(mappedBy = "comment")
    private List<CommentLike> commentLikeList = new ArrayList<>();



//    public  void setMemo(Memo memo){
//        this.memo = memo;
//        memo.getComments().add(this);
//    }
//
//
//    public void setUser(User user){
//        this.user = user;
//    }

    public Comment(CommentRequestDto requestDto,Memo memo,User user){
        this.content = requestDto.getComment();
        this.memo = memo;
        this.user = user;
    }

    public void update(CommentRequestDto requestDto) {
//        this.username = requestDto.getUsername();
        this.content = requestDto.getComment();

    }
    public void plusLike(){
        ++likes;
    }
    public void minusLike() {
        --likes;
    }
}
