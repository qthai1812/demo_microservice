package com.example.file_service.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ApiResponse<T> {

    @Builder.Default
    int code=1000;
    @Builder.Default
    String message="success";
    T result;

}
