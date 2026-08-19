package com.example.notification_service.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
public class AppException extends RuntimeException{

    private final ErrorCode errorCode;
    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
