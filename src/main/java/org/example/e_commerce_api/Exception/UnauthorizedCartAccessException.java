package org.example.e_commerce_api.Exception;

public class UnauthorizedCartAccessException extends RuntimeException {

    public UnauthorizedCartAccessException() {
        super("You are not authorized to access this cart item");
    }

    public UnauthorizedCartAccessException(String message) {
        super(message);
    }
}