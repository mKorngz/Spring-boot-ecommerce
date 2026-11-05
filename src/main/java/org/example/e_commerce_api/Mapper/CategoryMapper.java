package org.example.e_commerce_api.Mapper;

import org.example.e_commerce_api.DTO.Response.CategoryResponse;
import org.example.e_commerce_api.Entity.Categories;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public static CategoryResponse toCategoryResponse(Categories categories) {
        return CategoryResponse.builder()
                .id(categories.getId())
                .name(categories.getName())
                .description(categories.getDescription())
                .build();
    }
}
