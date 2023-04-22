package com.sparta.hanghaememo.service;

import com.sparta.hanghaememo.dto.StatusResponseDto;
import com.sparta.hanghaememo.dto.UserRequestDto;
import com.sparta.hanghaememo.entity.User;
import com.sparta.hanghaememo.jwt.JwtUtil;
import com.sparta.hanghaememo.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
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

}