package com.example.patika.exception;

public class AdvertNotFoundException extends RuntimeException{
    public AdvertNotFoundException(String message) {
        super(message);
    }
}
