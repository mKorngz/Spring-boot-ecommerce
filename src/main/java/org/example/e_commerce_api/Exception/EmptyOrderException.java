package org.example.e_commerce_api.Exception;

public class EmptyOrderException extends RuntimeException {

    public EmptyOrderException() {
        super("Order must contain at least one item");
    }

    public EmptyOrderException(String message) {
        super(message);
    }
}