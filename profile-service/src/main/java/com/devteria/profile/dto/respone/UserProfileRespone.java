package com.devteria.profile.dto.respone;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileRespone {
    String id;
    String userId;
    String username;
    String avatar;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
