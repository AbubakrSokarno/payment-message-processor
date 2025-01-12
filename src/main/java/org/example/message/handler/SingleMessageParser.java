package org.example.message.handler;

import org.example.entity.MessageEntity;

import static org.example.message.handler.MessageParserHelper.addTag;

public class SingleMessageParser {

    public MessageEntity parse(String message) {

        int currentIndex = 0;

        MessageEntity messageEntity = new MessageEntity();

        while (currentIndex < message.length()) {
            String tag = resolveTag(message, currentIndex);

            currentIndex += tag.length();
            String lengthField = resolveLength(message, currentIndex);

            currentIndex += lengthField.length();
            String value = resolveValue(message, currentIndex, lengthField);

            currentIndex += value.length();

            addTag(tag, value, messageEntity);
        }

        return messageEntity;
    }

    private String resolveTag(String message, int index) {
        int currentIndex = index;

        int tag = Integer.parseInt(message.substring(currentIndex, currentIndex + 2), 16);
        currentIndex += 2;

        boolean includeNextByte = true;
        for (int i = 0; i < 5; i++) {
            if ((tag & (1 << i)) == 0) {
                includeNextByte = false;
                break;
            }
        }

        int length = 1;
        while (includeNextByte) {
            int nextTagPart = Integer.parseInt(message.substring(currentIndex, currentIndex + 2), 16);
            if ((nextTagPart & (1 << 7)) == 0) {
                includeNextByte = false;
            }
            length++;
        }

        return message.substring(index, index + length * 2);
    }

    private String resolveLength(String message, int currentIndex) {
        int lengthField = Integer.parseInt(message.substring(currentIndex, currentIndex + 2), 16);

        if ((lengthField & (1 << 7)) == 0) {
            return message.substring(currentIndex, currentIndex + 2);
        }

        int lengthOfLengthField = lengthField & 127;

        return message.substring(currentIndex, currentIndex + 2 + lengthOfLengthField * 2);
    }

    private String resolveValue(String message, int currentIndex, String valueLength) {
        int length = 0;

        if (valueLength.length() == 2) {
            length = Integer.parseInt(valueLength, 16);
        } else {
            length = Integer.parseInt(valueLength.substring(2), 16);
        }

        return message.substring(currentIndex, currentIndex + length * 2);
    }
}
