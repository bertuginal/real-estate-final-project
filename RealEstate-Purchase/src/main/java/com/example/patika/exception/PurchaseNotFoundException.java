package com.example.patika.exception;

public class PurchaseNotFoundException extends RuntimeException{
    public PurchaseNotFoundException(String message) {
        super(message);
    }
}
