package org.example.message.decoder;

import org.example.interfaces.Decoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64MessageDecoder implements Decoder {

    public byte[] decode(String message) {
        if (isBase64(message)) {
            byte[] decoded = Base64.getDecoder().decode(message);
            return bytesToHex(decoded);
        }
        return message.getBytes();
    }

    private byte[] bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase().getBytes();
    }

    public boolean isBase64(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(input);
            String encoded = Base64.getEncoder().encodeToString(decoded);
            return input.equals(encoded);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}