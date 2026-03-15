package com.infy.card.management.system.exceptions;

public class UserAlreadyExists extends  RuntimeException{
    public UserAlreadyExists(String message) {
        super(message);
    }
}
