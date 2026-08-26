package com.devteria.identity.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.devteria.identity.configuration.AuthenticationRequestIntercepter;
import com.devteria.identity.dto.request.ProfileCreationRequest;
import com.devteria.identity.dto.response.UserProfileRespone;

// profile-service: port 8085, context-path: /profile
// → endpoint thực tế: http://profile-service:8085/profile/users
@FeignClient(
        name = "profile-service",
        url = "http://profile-service:8085",
        configuration = {AuthenticationRequestIntercepter.class})
public interface ProfileClient {
    @PostMapping(value = "/profile/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileRespone createUserProfile(@RequestBody ProfileCreationRequest request);
}
