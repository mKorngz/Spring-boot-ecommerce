package org.example.e_commerce_api.DTO.Request.Product_Request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock_quantity;
    private Long categoryId;
}
