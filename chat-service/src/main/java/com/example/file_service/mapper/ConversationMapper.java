package com.example.file_service.mapper;

import com.example.file_service.dto.response.ConversationResponse;
import com.example.file_service.entity.Conversation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConversationMapper {
    ConversationResponse toConversationResponse(Conversation conversation);
}
