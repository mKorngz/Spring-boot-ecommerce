package org.example.e_commerce_api.DTO.Response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse {
    private Long id;
    private Long productId;
    String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}
