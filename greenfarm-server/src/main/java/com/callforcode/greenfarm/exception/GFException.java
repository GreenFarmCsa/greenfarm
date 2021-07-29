package com.callforcode.greenfarm.exception;

public class GFException extends RuntimeException {

    public GFException(String message) {
        super(message);
    }

    public GFException(String message, Exception e) {
        super(message, e);
    }

}
