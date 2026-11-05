package org.example.e_commerce_api.Exception;

public class UnauthorizedOrderAccessException
        extends RuntimeException {

    public UnauthorizedOrderAccessException() {
        super("You are not authorized to access this order");
    }
}