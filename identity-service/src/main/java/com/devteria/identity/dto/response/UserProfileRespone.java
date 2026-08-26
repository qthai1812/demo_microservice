package com.devteria.identity.dto.response;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileRespone {
    String userId;
    String username;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
