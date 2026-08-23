package com.devteria.profile.controller;

import com.devteria.profile.dto.ApiResponse;
import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.service.UserProfileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
@RequestMapping("/users")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping
    ApiResponse<UserProfileRespone> createUserProfile(@RequestBody UserProfileRequest request){
       return ApiResponse.<UserProfileRespone>builder()
               .result(userProfileService.createUserProfile(request))
               .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserProfileRespone> getUserProfileByUserId(@PathVariable String userId){
        return ApiResponse.<UserProfileRespone>builder()
                .result(userProfileService.getUserProfileByUserId(userId))
                .build();
    }

    @GetMapping
    ApiResponse<List<UserProfileRespone>> getUserProfile() {
       return ApiResponse.<List<UserProfileRespone>>builder()
               .result(userProfileService.getUserProfile())
               .build();
    }
    @PostMapping("/update/avatar")
    ApiResponse<UserProfileRespone> updateAvatar(@RequestParam("file")MultipartFile file){
        return ApiResponse.<UserProfileRespone>builder()
                .result(userProfileService.updateAvatar(file))
                .build();
    }


}
