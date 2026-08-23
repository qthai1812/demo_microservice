package com.example.file_service.exception;

import com.example.file_service.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    ApiResponse<String> appExceptionHandler(AppException appException){
        return ApiResponse.<String>builder()
                .code(ErrorCode.ERROR_SYSTEM.getCode())
                .message(ErrorCode.ERROR_SYSTEM.getMessage()+" App Exception")
                .result(appException.getMessage())
                .build();
    }

    @ExceptionHandler(value = RuntimeException.class)
    ApiResponse<String> runtimeExceptionHandler(RuntimeException runtimeException){

        runtimeException.printStackTrace();

        return ApiResponse.<String>builder()
                .code(ErrorCode.ERROR_SYSTEM.getCode())
                .message(ErrorCode.ERROR_SYSTEM.getMessage()+" Runtime Exception")
                .result(runtimeException.getMessage())
                .build();
    }
}
