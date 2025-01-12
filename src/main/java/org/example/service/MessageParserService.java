package org.example.service;

import org.example.entity.MessageResponseDTO;
import org.example.mapper.MessageResponseMapper;
import org.example.message.handler.MultipleMessageParser;
import org.springframework.stereotype.Service;

@Service
public class MessageParserService {
    private final MultipleMessageParser multipleMessageParser;

    public MessageParserService(MultipleMessageParser multipleMessageParser) {
        this.multipleMessageParser = multipleMessageParser;
    }

    public MessageResponseDTO parseMessage(String message) {
        return MessageResponseMapper.toMessageResponse(multipleMessageParser.parse(message));
    }
}
