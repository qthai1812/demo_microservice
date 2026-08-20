package com.devteria.profile.exception;

import com.devteria.profile.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ApiResponse<Void> runtimeExceptionHandler(RuntimeException runtimeException){
        return ApiResponse.<Void>builder()
                .code(ErrorCode.SYSTEM_ERROR.getCode())
                .message(ErrorCode.SYSTEM_ERROR.getMessage())
                .build();
    }
    @ExceptionHandler(value = AppException.class)
    ApiResponse<Void> appExceptionHandler(AppException appException){
        return ApiResponse.<Void>builder()
                .code(ErrorCode.SYSTEM_ERROR.getCode())
                .message(ErrorCode.SYSTEM_ERROR.getMessage())
                .build();
    }
}
