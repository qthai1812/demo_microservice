package com.devteria.profile.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.devteria.profile.dto.request.ProfileCreationRequest;
import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.FileResponse;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.dto.response.UserProfileResponse;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.exception.AppException;
import com.devteria.profile.exception.ErrorCode;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import com.devteria.profile.repository.httpclient.FileClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC, makeFinal = true)
public class UserProfileService {
    UserProfileRepository userProfileRepository;
    UserProfileMapper userProfileMapper;
    FileClient fileClient;

    public UserProfileRespone createUserProfile(UserProfileRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileRespone(userProfile);
    }

    public UserProfileRespone getUserProfileByUserId(String userId) {
        UserProfile userProfile = userProfileRepository
                .findUserProfileByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_PROFILE_NOT_FOUND));
        return userProfileMapper.toUserProfileRespone(userProfile);
    }

    public List<UserProfileRespone> getUserProfile() {
        return userProfileRepository.findAll().stream()
                .map(userProfileMapper::toUserProfileRespone)
                .toList();
    }

    public UserProfileRespone updateAvatar(MultipartFile file) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt = (Jwt) authentication.getPrincipal();

        String userId = jwt.getClaim("userId");

        UserProfile userProfile = userProfileRepository
                .findUserProfileByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_PROFILE_NOT_FOUND));

        FileResponse fileResponse = fileClient.uploadAvatar(file).getResult();

        userProfile.setAvatar(fileResponse.getUrl());

        return userProfileMapper.toUserProfileRespone(userProfile);
    }

    public UserProfileResponse createProfile(ProfileCreationRequest request) {
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileResponse(userProfile);
    }

    public UserProfileResponse getByUserId(String userId) {
        UserProfile userProfile = userProfileRepository
                .findUserProfileByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_PROFILE_NOT_FOUND));
        return userProfileMapper.toUserProfileResponse(userProfile);
    }
}
