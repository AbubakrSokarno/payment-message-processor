package org.example.entity;

import java.util.List;
import java.util.Map;

public class MessageResponseDTO {
    private Map<String, List<MessageEntity>> response;

    public Map<String, List<MessageEntity>> getResponse() {
        return response;
    }

    public void setResponse(Map<String, List<MessageEntity>> response) {
        this.response = response;
    }
}
