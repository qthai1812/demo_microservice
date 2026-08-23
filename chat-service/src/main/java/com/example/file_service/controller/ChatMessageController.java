package com.example.file_service.controller;

import com.example.file_service.dto.ApiResponse;
import com.example.file_service.dto.request.ChatMessageRequest;
import com.example.file_service.dto.response.ChatMessageResponse;
import com.example.file_service.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ChatMessageController {
    ChatMessageService chatMessageService;

    @PostMapping("/create")
    ApiResponse<ChatMessageResponse> create(@RequestBody @Valid ChatMessageRequest request){
        return ApiResponse.<ChatMessageResponse>builder()
                .result(chatMessageService.create(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<ChatMessageResponse>> getAllMessages(@RequestParam String conversationId){
        return ApiResponse.<List<ChatMessageResponse>>builder()
                .result(chatMessageService.getAllMessages(conversationId))
                .build();
    }
}
