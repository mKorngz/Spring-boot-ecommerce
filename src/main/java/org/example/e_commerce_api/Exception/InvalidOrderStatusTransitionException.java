package org.example.e_commerce_api.Exception;

public class InvalidOrderStatusTransitionException extends RuntimeException {

    public InvalidOrderStatusTransitionException() {
        super("Invalid order status transition");
    }

    public InvalidOrderStatusTransitionException(String message) {
        super(message);
    }
}