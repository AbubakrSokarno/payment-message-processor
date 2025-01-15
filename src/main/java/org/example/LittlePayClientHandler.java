package org.example;

import org.example.service.MessageParserService;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
public class LittlePayClientHandler {
    private final MessageParserService messageParserService;

    public LittlePayClientHandler(MessageParserService messageParserService) {
        this.messageParserService = messageParserService;
    }

    public void run(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                messageParserService.parseMessage(inputLine.trim());
            }
        }
    }
}
