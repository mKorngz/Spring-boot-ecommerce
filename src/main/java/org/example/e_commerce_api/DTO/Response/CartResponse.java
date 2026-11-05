package org.example.e_commerce_api.DTO.Response;

import lombok.*;
import org.example.e_commerce_api.Entity.Cart_items;
import org.example.e_commerce_api.Entity.Users;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private Long id;
    private Long userId;
    private List<CartItemResponse> cartItems;
    private BigDecimal total;
}

