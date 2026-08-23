package com.example.file_service.repository.httpclient;

import com.example.file_service.configuration.FeignClientInterceptor;
import com.example.file_service.dto.ApiResponse;
import com.example.file_service.dto.response.UserProfileRespone;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "profile-service",url = "http://localhost:8081/profile",configuration = FeignClientInterceptor.class)
public interface UserProfileClient {
    @GetMapping("/users/{userId}")
    ApiResponse<UserProfileRespone> getUserProfile(@PathVariable String userId);
}
