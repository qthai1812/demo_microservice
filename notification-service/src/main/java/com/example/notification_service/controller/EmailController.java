package com.example.notification_service.controller;

import com.example.notification_service.dto.ApiResponse;
import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.response.EmailResponse;
import com.example.notification_service.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping
    ApiResponse<EmailResponse> sendEmail(@RequestBody EmailRequest request){
        return ApiResponse.<EmailResponse>builder()
                .code(1000)
                .message("sucess")
                .result(emailService.sendEmail(request))
                .build();
    }
}
