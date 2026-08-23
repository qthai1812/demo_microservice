package com.example.file_service.service;

import com.example.file_service.dto.request.ChatMessageRequest;
import com.example.file_service.dto.response.ChatMessageResponse;
import com.example.file_service.entity.ChatMessage;
import com.example.file_service.entity.ParticipantInfo;
import com.example.file_service.exception.AppException;
import com.example.file_service.exception.ErrorCode;
import com.example.file_service.mapper.ChatMessageMapper;
import com.example.file_service.repository.ChatMessageRepository;
import com.example.file_service.repository.ConversationRepository;
import com.example.file_service.repository.httpclient.UserProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ChatMessageService {
    ChatMessageRepository chatMessageRepository;
    ChatMessageMapper chatMessageMapper;
    ConversationRepository conversationRepository;
    UserProfileClient userProfileClient;

    public List<ChatMessageResponse> getAllMessages(String conversationId){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getClaim("userId");

        var conversation = conversationRepository.findById(conversationId).orElseThrow(() ->
                new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        conversation.getParticipants().stream()
                .filter(participantInfo -> currentUserId.equals(participantInfo.getUserId()))
                .findAny().orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        var userResponse =userProfileClient.getUserProfile(currentUserId);
        if(Objects.isNull(userResponse))
            throw new AppException(ErrorCode.USER_NOT_FOUND);

        var messages = chatMessageRepository.findAllByConversationIdOrderByCreatedDateDesc(conversationId);

        return messages.stream().map(this::toChatMessageResponse).toList();
    }

    public ChatMessageResponse create(ChatMessageRequest request){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getClaim("userId");

        var conversation = conversationRepository.findById(request.getConversationId()).orElseThrow(() ->
                new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        conversation.getParticipants().stream()
                .filter(participantInfo -> currentUserId.equals(participantInfo.getUserId()))
                .findAny().orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        var userResponse =userProfileClient.getUserProfile(currentUserId);
        if(Objects.isNull(userResponse))
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        var userInfo =userResponse.getResult();

        ChatMessage chatMessage = chatMessageMapper.toChatMessage(request);
        chatMessage.setSender(ParticipantInfo.builder()
                        .userId(userInfo.getUserId())
                        .username(userInfo.getUsername())
                        .firstName(userInfo.getFirstName())
                        .lastName(userInfo.getLastName())
                        .avatar(userInfo.getAvatar())
                .build());
        chatMessage.setCreatedDate(Instant.now());

        chatMessageRepository.save(chatMessage);

        return toChatMessageResponse(chatMessage);
    }

    private ChatMessageResponse toChatMessageResponse(ChatMessage chatMessage){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getClaim("userId");

        var chatMessageResponse = chatMessageMapper.toChatMessageResponse(chatMessage);

        chatMessageResponse.setMe(currentUserId.equals(chatMessage.getSender().getUserId()));
        return chatMessageResponse;
    }
}
