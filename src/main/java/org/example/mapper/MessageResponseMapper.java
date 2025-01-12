package org.example.mapper;

import org.example.entity.MessageEntity;
import org.example.entity.MessageResponseDTO;

import java.util.List;
import java.util.Map;

public class MessageResponseMapper {
    public static MessageResponseDTO toMessageResponse(Map<String, List<MessageEntity>> messageEntityMap) {
        MessageResponseDTO response = new MessageResponseDTO();
        response.setResponse(messageEntityMap);
        return response;
    }
}