package org.example.e_commerce_api.Mapper;

import org.example.e_commerce_api.DTO.Response.CartItemResponse;
import org.example.e_commerce_api.Entity.Cart_items;
import org.example.e_commerce_api.Entity.Products;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartItemMapper {

    public static CartItemResponse toResponse(Cart_items cartItem) {
        if (cartItem == null) {
            return null;
        }

        Products product = cartItem.getProducts();

        // Extract product fields safely
        Long productId = (product != null) ? product.getId() : null;
        String productName = (product != null) ? product.getName() : null;
        BigDecimal price = (product != null) ? product.getPrice() : BigDecimal.ZERO;

        Integer quantity = cartItem.getQuantity() != null ? cartItem.getQuantity() : 0;

        // Calculate subtotal (price * quantity)
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));

        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(productId)
                .productName(productName)
                .price(price)
                .quantity(quantity)
                .subtotal(subtotal)
                .build();
    }
}