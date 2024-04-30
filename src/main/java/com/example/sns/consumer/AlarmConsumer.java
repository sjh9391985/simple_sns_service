package com.example.sns.consumer;

import com.example.sns.model.event.AlarmEvent;
import com.example.sns.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumerAlarm(AlarmEvent event, Acknowledgment ack) {
        alarmService.send(event.getAlarmType(), event.getArgs(), event.getReceiveUserId());
        ack.acknowledge();
    }
}
