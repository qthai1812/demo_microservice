package com.example.file_service.exception;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public enum ErrorCode {

    ERROR_SYSTEM(9999,"ERROR IN FILE SERVICE",HttpStatus.BAD_REQUEST),
    FILE_NOT_FOUND(1001,"FILE NOT EXISTS IN DATABASE",HttpStatus.BAD_REQUEST),
    ERROR_CODE(1003,"UNAUTHENTICATED REQUEST",HttpStatus.FORBIDDEN),
    ;


    int code;
    String message;
    HttpStatus httpStatus;
}
