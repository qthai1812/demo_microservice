package com.example.file_service.service;

import com.example.file_service.dto.request.ConversationRequest;
import com.example.file_service.dto.response.ConversationResponse;
import com.example.file_service.entity.Conversation;
import com.example.file_service.entity.ParticipantInfo;
import com.example.file_service.exception.AppException;
import com.example.file_service.exception.ErrorCode;
import com.example.file_service.mapper.ConversationMapper;
import com.example.file_service.repository.ConversationRepository;
import com.example.file_service.repository.httpclient.UserProfileClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
public class ConversationService {
    ConversationRepository conversationRepository;
    UserProfileClient userProfileClient;
    ConversationMapper conversationMapper;

    public ConversationResponse create(ConversationRequest request){

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userId = jwt.getClaim("userId");


        var userProfileRespone =   userProfileClient.getUserProfile(userId);

        var participantResponse = userProfileClient.getUserProfile(request.getParticipantsId().getFirst());

        if(Objects.isNull(userProfileRespone) || Objects.isNull(participantResponse))
            throw new AppException(ErrorCode.USER_NOT_FOUND);

        var userInfo = userProfileRespone.getResult();
        var participantInfo = participantResponse.getResult();

        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        userIds.add(participantInfo.getUserId());
        var sortIds = userIds.stream().sorted().toList();
        String userIdHash = generatePaticipantHash(sortIds);

        var conversation = conversationRepository.findByParticipantsHash(userIdHash).orElseGet(() ->
        {
            List<ParticipantInfo> participantInfos = List.of(
                    ParticipantInfo.builder()
                            .userId(userInfo.getUserId())
                            .username(userInfo.getUsername())
                            .firstName(userInfo.getFirstName())
                            .lastName(userInfo.getLastName())
                            .avatar(userInfo.getAvatar())
                            .build(),
                    ParticipantInfo.builder()
                            .userId(participantInfo.getUserId())
                            .username(userInfo.getUsername())
                            .firstName(participantInfo.getFirstName())
                            .lastName(participantInfo.getLastName())
                            .avatar(participantInfo.getAvatar())
                            .build()
            );

            Conversation newConversation = Conversation.builder()
                    .type(request.getType())
                    .participantsHash(userIdHash)
                    .createdDate(Instant.now())
                    .modifiedDate(Instant.now())
                    .participants(participantInfos)
                    .build();
           return conversationRepository.save(newConversation);

        });

        return toConversationResponse(conversation);
    }
    public List<ConversationResponse> getAllConversations(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getClaim("userId");

        List<Conversation> conversations = conversationRepository.findAllByParticipantIdsContains(currentUserId);

        return conversations.stream().map(this::toConversationResponse).toList();
    }


    private String generatePaticipantHash(List<String> ids){
        StringJoiner stringJoiner = new StringJoiner("_");
        ids.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
    private ConversationResponse toConversationResponse(Conversation conversation) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String currentUserId = jwt.getClaim("userId");

        ConversationResponse conversationResponse = conversationMapper.toConversationResponse(conversation);

        conversation.getParticipants().stream()
                .filter(participantInfo -> !participantInfo.getUserId().equals(currentUserId))
                .findFirst().ifPresent(participantInfo -> {
                    conversationResponse.setConversationName(participantInfo.getUsername());
                    conversationResponse.setConversationAvatar(participantInfo.getAvatar());
                });

        return conversationResponse;
    }

}
