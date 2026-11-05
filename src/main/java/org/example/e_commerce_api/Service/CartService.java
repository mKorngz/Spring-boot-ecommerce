package org.example.e_commerce_api.Service;

import org.example.e_commerce_api.DTO.Request.Cart_Request.AddCartItemRequest;
import org.example.e_commerce_api.DTO.Request.Cart_Request.UpdateCartItemRequest;
import org.example.e_commerce_api.DTO.Response.CartItemResponse;
import org.example.e_commerce_api.DTO.Response.CartResponse;

public interface CartService {
    CartResponse viewMyCart();
    CartItemResponse addItemToCart(AddCartItemRequest updateCartItemRequest);
    CartItemResponse updateCartItem(Long itemId, UpdateCartItemRequest updateCartItemRequest);
    void deleteCartItem(Long id);
}
