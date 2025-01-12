package org.example;

import org.example.message.handler.MultipleMessageParser;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Component
public class LittlePayClientHandler {
    private final MultipleMessageParser multipleMessageParser;

    public LittlePayClientHandler(MultipleMessageParser multipleMessageParser) {
        this.multipleMessageParser = multipleMessageParser;
    }

    public void run(Socket clientSocket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                multipleMessageParser.parse(inputLine.trim());
            }
        }
    }
}
