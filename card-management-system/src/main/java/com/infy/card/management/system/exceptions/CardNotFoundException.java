package com.infy.card.management.system.exceptions;

public class CardNotFoundException extends RuntimeException {

    private final String message;

    public CardNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
