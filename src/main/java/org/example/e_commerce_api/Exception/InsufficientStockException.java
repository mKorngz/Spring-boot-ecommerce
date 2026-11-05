package org.example.e_commerce_api.Exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException() {
        super("Insufficient stock");
    }

    public InsufficientStockException(String message) {
        super(message);
    }
}