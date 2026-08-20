package com.example.service;

import com.example.dto.PageResponse;
import com.example.dto.request.PostRequest;
import com.example.dto.response.PostResponse;
import com.example.dto.response.UserProfileRespone;
import com.example.entity.Post;
import com.example.mapper.PostMapper;
import com.example.repository.PostRepository;
import com.example.repository.httpclient.ProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class PostService {

    PostMapper postMapper;
    PostRepository postRepository;
    DateTimeFormartter dateTimeFormartter;
    ProfileClient profileClient;

    public PostResponse createPost(PostRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt =(Jwt) authentication.getPrincipal();

        String userId = jwt.getClaim("userId");

        Post post = Post.builder()
                .userId(userId)
                .content(request.getContent())
                .createdDate(Instant.now())
                .modifiedDate(Instant.now())
                .build();
        postRepository.save(post);

        return postMapper.toPostResponse(post);

    }
    public PageResponse<PostResponse> getAllPost(int page , int size){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Jwt jwt =(Jwt) authentication.getPrincipal();

        String userId = jwt.getClaim("userId");

        Sort sort = Sort.by("createdDate").descending();

        Pageable pageable = PageRequest.of(page-1,size,sort);

        var pageData = postRepository.findAllByUserId(userId,pageable);

        UserProfileRespone userProfileRespone = profileClient.getUserProfileByUserId(userId).getResult();

        return PageResponse.<PostResponse>builder()
                .currentPage(page)
                .totalPage(pageData.getTotalPages())
                .pageSize(size)
                .totalElements(pageData.getTotalElements())
                .data(pageData.map(post -> {
                    var postResponse = postMapper.toPostResponse(post);
                    postResponse.setCreated(dateTimeFormartter.formart(post.getCreatedDate()));
                    postResponse.setUserName(authentication.getName());
                    postResponse.setFirstName(userProfileRespone.getFirstName());
                    return postResponse;
                }).stream().toList())
                .build();
    }

}
