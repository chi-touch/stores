package org.chi.exceptions;

public class UserUpdateFailedException extends RuntimeException{
    public UserUpdateFailedException(String message) {
        super(message);
    }
}
