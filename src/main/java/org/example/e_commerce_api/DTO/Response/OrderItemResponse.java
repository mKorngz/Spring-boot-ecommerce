package org.example.e_commerce_api.DTO.Response;

import lombok.*;
import org.example.e_commerce_api.Entity.Orders;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;

    private Long productId;

    private String product_name;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal subTotal;
}
