package org.example;

import org.example.entity.MessageEntity;
import org.example.entity.MessageResponseDTO;
import org.example.mapper.MessageResponseMapper;
import org.example.message.decoder.Base64MessageDecoder;
import org.example.message.handler.MultipleMessageParser;
import org.example.service.MessageParserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MessageParserServiceTest {

    @InjectMocks
    private MessageParserService messageParserService;

    @Mock
    private MultipleMessageParser multipleMessageParser;

    @Mock
    private Base64MessageDecoder messageDecoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseMessage() {
        String inputMessage = "test message";

        Map<String, List<MessageEntity>> parsedResult = new HashMap<>();
        parsedResult.put("key1", Arrays.asList(
                new MessageEntity("Mastercard", "34567890123456", 50.0f, "USD"),
                new MessageEntity("Mastercard", "4111111111111111", 1.0f, "EUR")
        ));
        parsedResult.put("key2", Collections.singletonList(
                new MessageEntity("Amex", "378282246310005f", 1234.56f, "GBP")
        ));

        MessageResponseDTO expectedResponse = new MessageResponseDTO();
        expectedResponse.setResponse(parsedResult);

        byte[] expectedDecodedBytes = new byte[]{1, 2, 3};
        when(messageDecoder.decode(inputMessage)).thenReturn(expectedDecodedBytes);
        when(multipleMessageParser.parse(anyString())).thenReturn(parsedResult);

        try (MockedStatic<MessageResponseMapper> mockedMapper = Mockito.mockStatic(MessageResponseMapper.class)) {
            mockedMapper.when(() -> MessageResponseMapper.toMessageResponse(parsedResult))
                    .thenReturn(expectedResponse);

            MessageResponseDTO actualResponse = messageParserService.parseMessage(inputMessage);

            assertEquals(expectedResponse, actualResponse);
            verify(messageDecoder).decode(inputMessage);
            verify(multipleMessageParser).parse(new String(expectedDecodedBytes));
            mockedMapper.verify(() -> MessageResponseMapper.toMessageResponse(parsedResult));
        }
    }
}