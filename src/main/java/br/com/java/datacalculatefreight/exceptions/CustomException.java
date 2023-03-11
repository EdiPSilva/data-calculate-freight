package br.com.java.datacalculatefreight.exceptions;

public class CustomException extends RuntimeException {
    public CustomException() {
    }

    public CustomException(String message) {
        super(message);
    }
}
