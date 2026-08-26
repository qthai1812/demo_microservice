package com.devteria.profile.mapper;

import org.mapstruct.Mapper;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.entity.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileRespone toUserProfileRespone(UserProfile userProfile);

    UserProfile toUserProfile(UserProfileRequest userProfileRequest);
}
