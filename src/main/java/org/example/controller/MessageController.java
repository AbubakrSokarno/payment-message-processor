package org.example.controller;


import org.example.entity.MessageRequest;
import org.example.entity.MessageResponseDTO;
import org.example.mapper.MessageResponseMapper;
import org.example.message.handler.MultipleMessageParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MultipleMessageParser multipleMessageParser = new MultipleMessageParser();

    @PostMapping("/parse")
    public ResponseEntity<MessageResponseDTO> parseMessage(@RequestBody MessageRequest request) {
        String message = request.getMessage();
        return ResponseEntity.ok(MessageResponseMapper.toMessageResponse(multipleMessageParser.parse(message)));
    }
}