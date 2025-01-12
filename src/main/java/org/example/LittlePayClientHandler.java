package org.example;

import org.example.message.handler.MultipleMessageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class LittlePayClientHandler {
    private final MultipleMessageParser multipleMessageParser = new MultipleMessageParser();

    public void run(Socket clientSocket) throws IOException {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                multipleMessageParser.parse(inputLine.trim());
            }
        }
    }
}
