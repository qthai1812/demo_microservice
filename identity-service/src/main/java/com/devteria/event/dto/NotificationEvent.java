package com.devteria.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class NotificationEvent {
    String channel;
    String recipient;
    // String templateCode;
    // Map<String,Object> param;
    String subject;
    String body;
}
