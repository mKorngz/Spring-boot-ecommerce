package org.example.e_commerce_api.Service;

import org.example.e_commerce_api.DTO.Request.CategoryRequest;
import org.example.e_commerce_api.DTO.Response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse save(CategoryRequest categoryRequest);
    CategoryResponse update(CategoryRequest categoryRequest, Long id);
    CategoryResponse findById(Long id);
    List<CategoryResponse> findAll();
    void deleteById(Long id);
}
