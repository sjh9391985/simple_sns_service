package com.example.sns.service;

import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.Alarms;
import com.example.sns.model.User;
import com.example.sns.model.entity.AlarmEntityRepository;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.respository.UserEntityRepository;
import com.example.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;


    @Transactional
    public User join(String userName, String password) {
        // 1. 이미 가입된 회원 찾기
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        // 2. 회원가입
        UserEntity user = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));

        return User.fromEntity(user);
    }

    public String login(String userName, String password) {
        // 1. 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // 2. 비밀번호 체크
        if (encoder.matches(password, userEntity.getPassword()))
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD, "password is invalid");

        // 3. 토큰 생성 및 반환
        return JwtTokenUtils.generateToken(userName, secretKey, expiredTimeMs);
    }

    public User loadUserByName(String userName) {
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
    }

    public Page<Alarms> alarmList(Pageable pageable, String userName) {
        // 1. 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        return alarmEntityRepository.findAllByUser(userEntity, pageable).map(Alarms::fromEntity);

    }

}
