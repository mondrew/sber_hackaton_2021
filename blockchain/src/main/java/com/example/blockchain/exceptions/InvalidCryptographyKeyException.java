package com.example.blockchain.exceptions;

public class InvalidCryptographyKeyException extends RuntimeException {
    public InvalidCryptographyKeyException(String message) {
        super(message);
    }
}
