package com.task.innowise.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super("Could not find user " + message);
    }
}
