package com.example.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "User name is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"user not founded"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "password is invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is invalid"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not founded"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permision is invalid"),
    ALARM_CONNECT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Connecting alarm occurs error"),
    ALREADY_LIKED(HttpStatus.CONFLICT, "User already liked the post" );

    private final HttpStatus status;
    private final String message;


}
