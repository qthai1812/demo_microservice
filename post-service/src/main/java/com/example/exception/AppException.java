package com.example.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;


@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AppException extends RuntimeException{
    ErrorCode errorCode;
    AppException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
