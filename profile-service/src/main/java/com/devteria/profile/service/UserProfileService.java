package com.devteria.profile.service;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileRespone createUserProfile(UserProfileRequest request){
        UserProfile userProfile = userProfileMapper.toUserProfile(request);
        userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileRespone(userProfile);
    }

    public UserProfileRespone getUserProfileByUserId(String userId){
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);
        return userProfileMapper.toUserProfileRespone(userProfile);
    }

    public List<UserProfileRespone> getUserProfile(){

        //var securityContextHolder = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().toList();
        //log.info("scope: {}",securityContextHolder);

       return userProfileRepository.findAll().stream().map(userProfileMapper::toUserProfileRespone).toList();
    }
}
