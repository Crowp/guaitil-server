package com.guaitilsoft.exceptions;

public class UserExistsException extends Exception {
    private static final long serialVersionUID = 1904585489531578456L;

    public UserExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserExistsException(String message) {
        super(message);
    }
}
