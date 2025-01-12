package org.example;

import org.example.entity.MessageEntity;
import org.example.entity.MessageResponseDTO;
import org.example.mapper.MessageResponseMapper;
import org.example.message.handler.MultipleMessageParser;
import org.example.service.MessageParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageParserServiceTest {

    @InjectMocks
    private MessageParserService messageParserService; // Service under test

    @Mock
    private MultipleMessageParser multipleMessageParser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseMessage() {
        // Arrange
        String inputMessage = "test message";

        // Mocked parsed result from MultipleMessageParser
        Map<String, List<MessageEntity>> parsedResult = new HashMap<>();
        parsedResult.put("key1", Arrays.asList(new MessageEntity("Mastercard", "34567890123456", 50.0f, "USD"), new MessageEntity("Mastercard", "4111111111111111", 1.0f, "EUR")));
        parsedResult.put("key2", Collections.singletonList(new MessageEntity("Amex", "378282246310005f", 1234.56f, "GBP")));

        MessageResponseDTO expectedResponse = new MessageResponseDTO();

        when(multipleMessageParser.parse(inputMessage)).thenReturn(parsedResult);
        mockStatic(MessageResponseMapper.class);
        when(MessageResponseMapper.toMessageResponse(parsedResult)).thenReturn(expectedResponse);

        MessageResponseDTO actualResponse = messageParserService.parseMessage(inputMessage);

        assertEquals(expectedResponse, actualResponse);
        verify(multipleMessageParser).parse(inputMessage);
    }
}
