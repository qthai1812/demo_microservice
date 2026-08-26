package com.example.repository.httpclient;

import com.example.dto.ApiResponse;
import com.example.dto.response.UserProfileRespone;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// profile-service: port 8085, context-path: /profile
// → endpoint thực tế: http://profile-service:8085/profile/users/{userId}
@FeignClient(value = "profile-service", url = "http://profile-service:8085")
public interface ProfileClient {
    @GetMapping(value = "/profile/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileRespone> getUserProfileByUserId(@PathVariable String userId);
}
