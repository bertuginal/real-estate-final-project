package com.example.patika.exception;

public class PaymentFailedException extends RuntimeException{

    public PaymentFailedException(String message) {
        super(message);
    }
}
