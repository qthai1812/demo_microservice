package com.example.exception;

import com.example.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ApiResponse<Void> runtimeExceptionHandler(RuntimeException runtimeException){
        runtimeException.printStackTrace();
        return ApiResponse.<Void>builder()
                .code(ErrorCode.SYSTEMERROR.getCode())
                .message(ErrorCode.SYSTEMERROR.getMessage())
                .build();
    }
    @ExceptionHandler(value = AppException.class)
    ApiResponse<Void> appExceptionHandler(AppException appException){
        return ApiResponse.<Void>builder()
                .code(ErrorCode.UNAUTHENTICATION.getCode())
                .message((ErrorCode.UNAUTHENTICATION.getMessage()))
                .build();
    }
}
