package com.devteria.profile.mapper;

import com.devteria.profile.dto.request.UserProfileRequest;
import com.devteria.profile.dto.respone.UserProfileRespone;
import com.devteria.profile.entity.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileRespone toUserProfileRespone(UserProfile userProfile);
    UserProfile toUserProfile(UserProfileRequest userProfileRequest);
}
