package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.PageResponse;
import com.example.dto.request.PostRequest;
import com.example.dto.response.PostResponse;
import com.example.service.PostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my_post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class PostController {
    PostService postService;

    @PostMapping
    ApiResponse<PostResponse> createPost(@RequestBody PostRequest request){
        return ApiResponse.<PostResponse>builder()
                .result(postService.createPost(request))
                .build();
    }
    @GetMapping
    ApiResponse<PageResponse<PostResponse>> getAllPost(
            @RequestParam(value = "page",required = false,defaultValue = "1") int page,
            @RequestParam(value = "size",required = false,defaultValue = "3") int size
                                               ){
        return ApiResponse.<PageResponse<PostResponse>>builder()
                .result(postService.getAllPost(page, size))
                .build();
    }
}
