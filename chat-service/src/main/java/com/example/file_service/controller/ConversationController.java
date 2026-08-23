package com.example.file_service.controller;

import com.example.file_service.dto.ApiResponse;
import com.example.file_service.dto.request.ConversationRequest;
import com.example.file_service.dto.response.ConversationResponse;
import com.example.file_service.service.ConversationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("conversation")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ConversationController {
    ConversationService conversationService;

    @PostMapping("/create")
    ApiResponse<ConversationResponse> createConversation(@RequestBody @Valid ConversationRequest request){
        return ApiResponse.<ConversationResponse>builder()
                .result(conversationService.create(request))
                .build();
    }
    @GetMapping("/my-conversations")
    ApiResponse<List<ConversationResponse>> createConversation(){
        return ApiResponse.<List<ConversationResponse>>builder()
                .result(conversationService.getAllConversations())
                .build();
    }
}
