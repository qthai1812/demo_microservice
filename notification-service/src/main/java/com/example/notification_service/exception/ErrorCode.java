package com.example.notification_service.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public enum ErrorCode {

    UNAUTHENTICATION(1001,"Error system",HttpStatus.FORBIDDEN),

    ;
    int code;
    String message;
    HttpStatus httpStatus;
}
