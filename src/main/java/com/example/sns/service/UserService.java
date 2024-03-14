package com.example.sns.service;

import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.User;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.respository.UserEntityRepository;
import javafx.beans.value.ObservableBooleanValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;

    public User join(String username, String password) {
        // 1. 이미 가입된 회원 찾기
        userEntityRepository.findByUsername(username).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        // 2. 회원가입
        UserEntity user = userEntityRepository.save(UserEntity.of(username, password));

        return User.fromEntity(user);
    }

    public String login(String username, String password) {
        // 1. 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUsername(username).orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,""));

        // 2. 비밀번호 체크
        if (userEntity.getPassword().equals(password)) throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME,"");

        // 3. 토큰 생성 및 반환
        return "";
    }

}
