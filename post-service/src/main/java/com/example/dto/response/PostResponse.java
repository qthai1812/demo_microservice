package com.example.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    String id;
    String userId;
    String userName;
    String firstName;
    String content;
    String created;
    Instant createdDate;
    Instant modifiedDate;
}
