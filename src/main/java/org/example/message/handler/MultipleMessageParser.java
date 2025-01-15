package org.example.message.handler;

import org.example.entity.MessageEntity;
import org.example.message.decoder.Base64MessageDecoder;
import org.example.interfaces.Decoder;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MultipleMessageParser {

    private static final String START = "02";
    private static final String END = "03";

    private final SingleMessageParser singleMessageParser;
    private final Decoder messageDecoder;

    public MultipleMessageParser(SingleMessageParser singleMessageParser, Base64MessageDecoder messageDecoder) {
        this.singleMessageParser = singleMessageParser;
        this.messageDecoder = messageDecoder;
    }

    public Map<String, List<MessageEntity>> parse(String messages) {
        byte[] decodedBytes = messageDecoder.decode(messages);
        String decodedMessages = new String(decodedBytes);
        validateTotalLength(decodedMessages);

        int currentIndex = 4;
        Map<String, List<MessageEntity>> messageEntityList = new HashMap<>();

        while (currentIndex < decodedMessages.length()) {
            String message = validateAndResolveMessage(currentIndex, decodedMessages);

            MessageEntity entity = singleMessageParser.parse(message);
            if (!messageEntityList.containsKey(entity.getKernel())) {
                messageEntityList.put(entity.getKernel(), new ArrayList<>());
            }

            messageEntityList.get(entity.getKernel()).add(entity);

            currentIndex += message.length() + 6;
        }

        for (String kernel : messageEntityList.keySet()) {
            System.out.println(messageEntityList.get(kernel));
        }

        return messageEntityList;
    }

    private void validateTotalLength(String message) {
        int totalLength = message.length(); //e.g. 0042

        if (totalLength <= 4) {
            throw new InvalidParameterException("Invalid message: length field should be of 2 bytes");
        }

        int lenOfMessage = Integer.parseInt(message.substring(0, 4), 16) * 2;

        if (totalLength - 4 != lenOfMessage) {
            throw new InvalidParameterException("Invalid message: length field does not match the length of the message");
        }
    }

    private String validateAndResolveMessage(int currentIndex, String hexMessage) {
        String start = hexMessage.substring(currentIndex, currentIndex + 2);

        if (!start.equals(START)) {
            throw new InvalidParameterException("Invalid message: start of the message not found");
        }

        int length = Integer.parseInt(hexMessage.substring(currentIndex + 2, currentIndex + 4), 16) * 2;
        currentIndex += 4 + length;

        String end = hexMessage.substring(currentIndex, currentIndex + 2);
        if (!end.equals(END)) {
            throw new InvalidParameterException("Invalid message: end of the message not found");
        }

        return hexMessage.substring(currentIndex - length, currentIndex);
    }
}
