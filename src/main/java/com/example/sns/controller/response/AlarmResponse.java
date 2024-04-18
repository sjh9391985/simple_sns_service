package com.example.sns.controller.response;

import com.example.sns.model.AlarmArgs;
import com.example.sns.model.AlarmType;
import com.example.sns.model.Alarms;
import com.example.sns.model.User;
import com.example.sns.model.entity.AlarmEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AlarmResponse {
    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;
    private String text;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    public static AlarmResponse fromAlarm(Alarms alarms) {
        return new AlarmResponse(
                alarms.getId(),
                alarms.getAlarmType(),
                alarms.getArgs(),
                alarms.getAlarmType().getAlarmText(),
                alarms.getRegisteredAt(),
                alarms.getUpdatedAt(),
                alarms.getDeletedAt()
        );
    }
}
