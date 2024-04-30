package com.example.sns.service;

import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.AlarmArgs;
import com.example.sns.model.AlarmType;
import com.example.sns.model.entity.AlarmEntity;
import com.example.sns.model.entity.AlarmEntityRepository;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.respository.EmitterRepository;
import com.example.sns.respository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";
    private final EmitterRepository emitterRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public void send(AlarmType type, AlarmArgs arg, Integer receiveUserId) {
        UserEntity user = userEntityRepository.findById(receiveUserId).orElseThrow(()->new SnsApplicationException(ErrorCode.USER_NOT_FOUND));
        AlarmEntity alarm = alarmEntityRepository.save(AlarmEntity.of(user,type, arg));
        emitterRepository.get(receiveUserId).ifPresent(sseEmitter ->  {
            try {
                sseEmitter.send(SseEmitter.event().id(String.valueOf(alarm.getId())).name("new alarm"));
            } catch (IOException e) {
                throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
            }
        });
    }

    public SseEmitter connectAlarm(Integer userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));

        try {
            sseEmitter.send(SseEmitter.event().id("").name(ALARM_NAME).data("connect completed"));
        } catch (IOException exception) {
            throw new SnsApplicationException(ErrorCode.ALARM_CONNECT_ERROR);
        }

        return sseEmitter;

    }


}
