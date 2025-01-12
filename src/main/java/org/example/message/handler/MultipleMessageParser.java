package org.example.message.handler;

import org.example.entity.MessageEntity;
import org.example.message.decoder.interfaces.Decoder;
import org.example.message.decoder.Base64MessageDecoder;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleMessageParser {

    private static final String START = "02";
    private static final String END = "03";

    private final SingleMessageParser singleMessageParser = new SingleMessageParser();
    private final Decoder messageDecoder = new Base64MessageDecoder();

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

        int lenOfMessage = Integer.parseInt(message.substring(0, 4), 16)*2;

        if(totalLength-4 != lenOfMessage){
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

/*
import org.example.entity.MessageEntity;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultipleMessageParser {

    private static final String START = "02";
    private static final String END = "03";

    private final SingleMessageParser singleMessageParser = new SingleMessageParser();

    public Map<String, List<MessageEntity>> parse(String messages){

        validateTotalLength(messages);

        int currentIndex = 4;
        Map<String, List<MessageEntity>> messageEntityList = new HashMap<>();

        while(currentIndex < messages.length()){
            String message  = validateAndResolveMessage(currentIndex, messages);

            MessageEntity entity = singleMessageParser.parse(message);
            if(!messageEntityList.containsKey(entity.getKernel())){
                messageEntityList.put(entity.getKernel(), new ArrayList<>());
            }

            messageEntityList.get(entity.getKernel()).add(entity);

            currentIndex += message.length() + 6;
        }

        for(String kernel : messageEntityList.keySet()){
            System.out.println(messageEntityList.get(kernel));
        }

        return messageEntityList;
    }

    private void validateTotalLength(String message) {
        int totalLength = message.length(); //e.g. 0042

        if(totalLength <= 4) {
            throw new InvalidParameterException("Invalid message: length field should be of 2 bytes");
        }

        int lenOfMessage = Integer.parseInt(message.substring(0, 4), 16);

        if(totalLength - 4 != lenOfMessage * 2){
            throw new InvalidParameterException("Invalid message: length field does not match the length of the message");
        }
    }

    private String validateAndResolveMessage(int currentIndex, String message) {
        String start = message.substring(currentIndex, currentIndex + 2);

        if(!start.equals(START)){
            throw new InvalidParameterException("Invalid message: start of the message not found");
        }
        currentIndex = 2;

        int length = Integer.parseInt(message.substring(currentIndex, currentIndex+2), 16)*2;
        currentIndex += 2 + length;

        String end = message.substring(currentIndex, currentIndex+2);
        if(!end.equals(END)){
            throw new InvalidParameterException("Invalid message: end of the message not found");
        }

        return message.substring(currentIndex-length, currentIndex);
    }
}
*/
