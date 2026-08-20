package com.example.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    UNAUTHENTICATION(1001,"USER NOT AUTHENTICATION",HttpStatus.FORBIDDEN),
    SYSTEMERROR(9999,"SYSTEM ERROR IN POST SERVICE",HttpStatus.BAD_REQUEST),
    ;

    int code;
    String message;
    HttpStatus httpStatus;
}
