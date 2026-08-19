package com.example.notification_service.service;

import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.response.EmailResponse;
import com.example.notification_service.repository.httpClient.EmailClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class EmailService {

    EmailClient emailClient;
    private static String apikey="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";

    public EmailResponse sendEmail(EmailRequest request){
       return emailClient.sendEmail(apikey,request);
    }

}
