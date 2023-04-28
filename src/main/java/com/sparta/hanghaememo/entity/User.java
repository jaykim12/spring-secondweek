package com.sparta.hanghaememo.entity;

import com.sparta.hanghaememo.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(UserRequestDto userRequestDto, UserRoleEnum role) {
        this.username = userRequestDto.getUsername();
        this.password = userRequestDto.getPassword();
        this.role = role;

    }
}
