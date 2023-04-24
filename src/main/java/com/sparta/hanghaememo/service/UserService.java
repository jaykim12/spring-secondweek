package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.dto.UserRequestDto;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public StatusResponseDto signup(UserRequestDto userRequestDto) {

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(userRequestDto.getUsername());
        if (found.isPresent()) {
            return new StatusResponseDto("이미 회원가입이 된 사용자입니다.", HttpStatus.ALREADY_REPORTED);
        }

        User user = new User(userRequestDto);
        userRepository.save(user);
        return new StatusResponseDto("회원가입을 성공적으로 마쳤습니다.", HttpStatus.OK);
    }

    @Transactional
    public StatusResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response) {
        String username = userRequestDto.getUsername();
        String password = userRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        // 비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw  new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //http header에 jwt 토큰 발급
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

        return new StatusResponseDto("로그인이 완료되었습니다.", HttpStatus.OK);
    }
}