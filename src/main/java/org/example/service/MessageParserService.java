package org.example.service;

import org.example.entity.MessageResponseDTO;
import org.example.interfaces.Decoder;
import org.example.mapper.MessageResponseMapper;
import org.example.message.decoder.Base64MessageDecoder;
import org.example.message.handler.MultipleMessageParser;
import org.springframework.stereotype.Service;

@Service
public class MessageParserService {
    private final MultipleMessageParser multipleMessageParser;
    private final Decoder messageDecoder;

    public MessageParserService(MultipleMessageParser multipleMessageParser, Base64MessageDecoder messageDecoder) {
        this.multipleMessageParser = multipleMessageParser;
        this.messageDecoder = messageDecoder;
    }

    public MessageResponseDTO parseMessage(String message) {
        byte[] decodedBytes = messageDecoder.decode(message);
        String decodedMessages = new String(decodedBytes);
        return MessageResponseMapper.toMessageResponse(multipleMessageParser.parse(decodedMessages));
    }
}
