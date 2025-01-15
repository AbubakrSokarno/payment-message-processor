package org.example;

import org.example.entity.MessageEntity;
import org.example.message.decoder.Base64MessageDecoder;
import org.example.message.handler.MultipleMessageParser;
import org.example.message.handler.SingleMessageParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class MultipleMessageParserTest {

    @Test
    public void shouldParseMessage() {
        Map<String, List<MessageEntity>> result = new MultipleMessageParser(new SingleMessageParser(), new Base64MessageDecoder())
                .parse("005d02189f2a01029f020201005a0841111111111111115f2a0209780302249f2a0804000000000000005f2a0208269f02031234565a08378282246310005f9f0301000302189f2a01029f02030050005a07345678901234565f2a02084003");

        Assertions.assertEquals(Map.of(
                "Amex", List.of(new MessageEntity("Amex", "378282246310005", 1234.56f, "GBP")),
                "Mastercard", List.of(new MessageEntity("Mastercard", "4111111111111111", 1.0f, "EUR"),
                        new MessageEntity("Mastercard", "34567890123456", 50.0f, "USD"))
        ), result);

    }

}