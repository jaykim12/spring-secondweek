package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "memo")
public class Memo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private Long id;

//    @Column(nullable = false) //이게 받는 값
//    private String username;

    @Column(nullable = false) //이게 받는 값
    private String contents;

    @Column(nullable = false) //이게 받는 값
    private String title;

    @ManyToOne(fetch = FetchType.LAZY) //이게 받는 값
    @JoinColumn(name = "username")
    private User user;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int likes = 0;

    @OneToMany(mappedBy = "memo",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments =new ArrayList<>();

    @OneToMany(mappedBy = "memo")
    private List<MemoLike> memoLikeList = new ArrayList<>();

    //MemoService에서 memoCreate 메서드가 requestDto를 인자로 전달하면
    // 해당 매개변수를 이용해 memo를 초기화 한다.
    public Memo(MemoRequestDto requestDto, User user) {
//        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
        this.user =user;
    }

    //MemoService.updateMemo 메서드를 사용할 때 인자로 전달되는 requestDto를
    //받아 메모를 수정하기 위한 메서드
    public void update(MemoRequestDto requestDto) {
//        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
        this.title = requestDto.getTitle();
    }

    public void plusLike(){
        ++likes;
    }
    public void minusLike() {
        --likes;
    }


}