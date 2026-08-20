package com.example.mapper;

import com.example.dto.response.PostResponse;
import com.example.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toPostResponse(Post post);
}
