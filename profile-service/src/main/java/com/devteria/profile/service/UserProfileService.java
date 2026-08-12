package com.devteria.profile.service;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.entity.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repository.UserProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public List<UserProfileRespone> getUserProfile(){
       return userProfileRepository.findAll().stream().map(userProfileMapper::toUserProfileRespone).toList();
    }
}
