package com.luebkemann.exceptions;

//WILL HANDLE THE EXCEPTION CAUGHT WHEN THE PASSWORD FIELD IS EMPTY
public class PasswordException extends RuntimeException {
    public PasswordException() {
    }

    public PasswordException(String message) {
        super(message);
    }
}
