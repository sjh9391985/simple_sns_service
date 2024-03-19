package com.example.sns.service;

import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.User;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.respository.UserEntityRepository;
import com.example.sns.util.JwtTokenUtils;
import javafx.beans.value.ObservableBooleanValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public User join(String username, String password) {
        // 1. 이미 가입된 회원 찾기
        userEntityRepository.findByUsername(username).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        // 2. 회원가입
        UserEntity user = userEntityRepository.save(UserEntity.of(username, encoder.encode(password)));

        return User.fromEntity(user);
    }

    public String login(String username, String password) {
        // 1. 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", username)));

        // 2. 비밀번호 체크
        if (encoder.matches(password, userEntity.getPassword()))
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD, "password is invalid");

        // 3. 토큰 생성 및 반환
        String token = JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);
        return token;
    }

}
