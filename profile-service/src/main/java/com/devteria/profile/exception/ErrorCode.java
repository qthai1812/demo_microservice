package com.devteria.profile.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    SYSTEM_ERROR(9999, "ERROR IN PROFILE SYSTEM", HttpStatus.BAD_REQUEST),
    USER_PROFILE_NOT_FOUND(1001, "USER PROFILE NOT FOUND IN DATABASE", HttpStatus.BAD_REQUEST),
    ;
    int code;
    String message;
    HttpStatus httpStatus;
}
