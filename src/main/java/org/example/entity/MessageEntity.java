package org.example.entity;

import java.util.Objects;

public class MessageEntity {

    private String kernel;
    private String cardNumber;
    private Float amount;
    private String currency;

    public MessageEntity() {
    }

    public MessageEntity(String kernel, String cardNumber, Float amount, String currency) {
        this.kernel = kernel;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return String.format("\nKernel: %s\nCard number: %s\nAmount: %.2f\nCurrency: %s\n", kernel, cardNumber, amount, currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageEntity that)) return false;
        return Objects.equals(kernel, that.kernel) && Objects.equals(cardNumber, that.cardNumber) && Objects.equals(amount, that.amount) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kernel, cardNumber, amount, currency);
    }
}
