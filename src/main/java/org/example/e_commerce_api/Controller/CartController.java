package org.example.e_commerce_api.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Request.Cart_Request.AddCartItemRequest;
import org.example.e_commerce_api.DTO.Request.Cart_Request.UpdateCartItemRequest;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.CartItemResponse;
import org.example.e_commerce_api.DTO.Response.CartResponse;
import org.example.e_commerce_api.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<BaseBodyResponse<CartResponse>> viewMyCart() {

        CartResponse response = cartService.viewMyCart();

        return BaseBodyResponse.success(
                response,
                "Cart retrieved successfully"
        );
    }

    @PostMapping("/items")
    public ResponseEntity<BaseBodyResponse<CartItemResponse>> addItemToCart(
            @Valid @RequestBody AddCartItemRequest request
    ) {

        CartItemResponse response =
                cartService.addItemToCart(request);

        return BaseBodyResponse.success(
                response,
                "Item added to cart successfully"
        );
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<BaseBodyResponse<CartItemResponse>> updateCartItem(
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        CartItemResponse response =
                cartService.updateCartItem(itemId, request);

        return BaseBodyResponse.success(
                response,
                "Cart item updated successfully"
        );
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<BaseBodyResponse<Void>> deleteCartItem(
            @PathVariable Long itemId
    ) {

        cartService.deleteCartItem(itemId);

        return BaseBodyResponse.success(
                "Cart item deleted successfully"
        );
    }
}