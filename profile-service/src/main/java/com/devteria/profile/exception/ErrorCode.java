package com.devteria.profile.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    SYSTEM_ERROR(9999,"ERROR IN PROFILE SYSTEM",HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatus httpStatus;
}
