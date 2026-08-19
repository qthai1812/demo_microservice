package com.example.notification_service.controller;

import com.example.event.dto.NotificationEvent;
import com.example.notification_service.dto.request.EmailRequest;
import com.example.notification_service.dto.request.Recipient;
import com.example.notification_service.dto.request.Sender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
    EmailController emailController;

    @KafkaListener(topics = "onboard-successfull")
    public void listenNotification(NotificationEvent notificationEvent){
        List<Recipient> recipients = new ArrayList<>();
        recipients.add(Recipient.builder()
                .email(notificationEvent.getRecipient())
                .name("username")
                .build());

        emailController.sendEmail(EmailRequest.builder()
                        .sender(Sender.builder()
                                .email("thaixuanson372@gmail.com")
                                .name("qthai1812")
                                .build())
                        .to(recipients)
                        .subject(notificationEvent.getSubject())
                        .textContent(notificationEvent.getBody())
                .build());
        log.info("NotificationEvent: {}",notificationEvent);
    }
}
