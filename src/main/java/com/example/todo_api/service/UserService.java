package com.example.todo_api.service;

import com.example.todo_api.domain.User;
import com.example.todo_api.domain.UserJoinRequestDto;
import com.example.todo_api.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void join(UserJoinRequestDto dto){
        // 중복 이메일 확인
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 엔티티 생성 및 저장
        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
    }
}
