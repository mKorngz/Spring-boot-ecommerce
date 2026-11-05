package org.example.e_commerce_api.Exception;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException() {
        super("Cart item not found");
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }
}