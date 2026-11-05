package org.example.e_commerce_api.Mapper;

import org.example.e_commerce_api.DTO.Response.ProductResponse;
import org.example.e_commerce_api.Entity.Products;

public class ProductMapper {
    public static ProductResponse toProductResponse(Products products) {
        return ProductResponse.builder()
                .id(products.getId())
                .name(products.getName())
                .price(products.getPrice())
                .stock_quantity(products.getStock_quantity())
                .category(CategoryMapper.toCategoryResponse(products.getCategories()))
                .build();
    }
}
