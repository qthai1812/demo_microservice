package com.devteria.profile.controller;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@RequestMapping("/users")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping
    UserProfileRespone createUserProfile(@RequestBody UserProfileRequest request){
       return userProfileService.createUserProfile(request);
    }
    @GetMapping
    List<UserProfileRespone> getUserProfile(){
       return userProfileService.getUserProfile();
    }

}
