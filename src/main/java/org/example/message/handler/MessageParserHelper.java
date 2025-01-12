package org.example.message.handler;

import org.example.entity.MessageEntity;

import java.util.Currency;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MessageParserHelper {

    private static Map<Integer, Currency> currencies = null;

    static {
        currencies = Currency.getAvailableCurrencies().stream().collect(Collectors.toMap(Currency::getNumericCode, Function.identity(), (key1, key2) -> key1));
    }

    public static String resolveKernel(String value){
        String kernelValue = value.substring(0, 2);

        return switch (kernelValue) {
            case "02" -> "Mastercard";
            case "03" -> "Visa";
            case "04" -> "Amex";
            default -> "unknown";
        };
    }

    public static String resolveCardNumber(String value, String kernel){
        return switch (kernel) {
            case "Amex" -> value.substring(0, 15);
            default -> value.substring(0, Integer.min(value.length(), 16));
        };
    }

    public static String resolveCurrency(String currency){
        int cur = Integer.parseInt(currency);

        if(currencies.containsKey(cur))  {
            return currencies.get(cur).getCurrencyCode();
        }

       return null;
    }

    public static Float resolveAmount(String amount){
        return Float.parseFloat(amount)/100;
    }

    public static void addTag(String tag, String value, MessageEntity messageEntity) {
        switch (tag.toUpperCase()){
            case "9F2A":
                messageEntity.setKernel(resolveKernel(value));
                break;
            case  "5A":
                messageEntity.setCardNumber(resolveCardNumber(value, resolveKernel(value)));
                break;
            case "9F02":
                messageEntity.setAmount(resolveAmount(value));
                break;
            case "5F2A":
                messageEntity.setCurrency(resolveCurrency(value));
                break;
            default:
                // do nothing
        }
    }
}
