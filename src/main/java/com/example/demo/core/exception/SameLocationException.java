package com.example.demo.core.exception;

public class SameLocationException extends RuntimeException {
    public SameLocationException() {
        super("Origin and destination locations cannot be the same.");
    }
}
