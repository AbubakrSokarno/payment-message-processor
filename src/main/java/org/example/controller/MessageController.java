package org.example.controller;


import org.example.entity.MessageRequest;
import org.example.entity.MessageResponseDTO;
import org.example.service.MessageParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageParserService messageParserService;

    public MessageController(MessageParserService messageParserService) {
        this.messageParserService = messageParserService;
    }

    @PostMapping("/parse")
    public ResponseEntity<MessageResponseDTO> parseMessage(@RequestBody MessageRequest request) {
        String message = request.getMessage();
        return ResponseEntity.ok(messageParserService.parseMessage(message));
    }
}