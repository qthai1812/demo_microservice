package com.example.notification_service.exception;

import com.example.notification_service.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ApiResponse<Void> runtimeExceptionHandler(RuntimeException runtimeException){
        return ApiResponse.<Void>builder()
                .code(ErrorCode.UNAUTHENTICATION.getCode())
                .message(ErrorCode.UNAUTHENTICATION.getMessage())
                .build();
    }
    @ExceptionHandler(value = AppException.class)
    ApiResponse<Void> appExceptionHandler(AppException appException){
        return ApiResponse.<Void>builder()
                .code(ErrorCode.UNAUTHENTICATION.getCode())
                .message(ErrorCode.UNAUTHENTICATION.getMessage())
                .build();
    }
}
