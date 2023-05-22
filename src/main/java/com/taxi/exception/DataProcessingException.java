package com.taxi.exception;

public class DataProcessingException extends RuntimeException {
    public DataProcessingException(String errMsg, Throwable throwable) {
        super(errMsg, throwable);
    }
}
