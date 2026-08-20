package com.example.repository.httpclient;

import com.example.dto.ApiResponse;
import com.example.dto.response.UserProfileRespone;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "profile-service",url = "http://localhost:8081/profile/users")
public interface ProfileClient {
    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileRespone> getUserProfileByUserId(@PathVariable String userId);
}
