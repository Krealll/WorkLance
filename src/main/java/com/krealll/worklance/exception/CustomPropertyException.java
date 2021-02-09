package com.krealll.worklance.exception;

public class CustomPropertyException extends Exception {

    public CustomPropertyException() {
    }

    public CustomPropertyException(String message) {
        super(message);
    }

    public CustomPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomPropertyException(Throwable cause) {
        super(cause);
    }
}
