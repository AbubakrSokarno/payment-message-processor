package org.example;

import org.example.entity.MessageEntity;
import org.example.entity.MessageResponseDTO;
import org.example.mapper.MessageResponseMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class MessageResponseMapperTest {
    @Test
    public void shouldMapMessageEntityMapToMessageResponse() {
        Map<String, List<MessageEntity>> input = Map.of(
                "Amex", List.of(new MessageEntity("Amex", "378282246310005f", 1234.56f, "GBP")),
                "Mastercard", List.of(
                        new MessageEntity("Mastercard", "4111111111111111", 1.0f, "EUR"),
                        new MessageEntity("Mastercard", "34567890123456", 50.0f, "USD")
                )
        );

        MessageResponseDTO response = MessageResponseMapper.toMessageResponse(input);

        Assertions.assertEquals(input, response.getResponse());
    }
}
