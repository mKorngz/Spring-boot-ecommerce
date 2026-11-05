package org.example.e_commerce_api.Controller;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.CategoryResponse;
import org.example.e_commerce_api.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<CategoryResponse>>>
    findAll() {

        return BaseBodyResponse.success(
                categoryService.findAll(),
                "Categories retrieved successfully"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<CategoryResponse>>
    findById(
            @PathVariable Long id
    ) {

        return BaseBodyResponse.success(
                categoryService.findById(id),
                "Category retrieved successfully"
        );
    }
}
