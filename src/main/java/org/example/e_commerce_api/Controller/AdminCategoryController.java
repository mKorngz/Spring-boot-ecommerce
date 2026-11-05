package org.example.e_commerce_api.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Request.CategoryRequest;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.CategoryResponse;
import org.example.e_commerce_api.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseBodyResponse<CategoryResponse>>
    createCategory(
            @Valid @RequestBody CategoryRequest request
    ) {

        return BaseBodyResponse.success(
                categoryService.save(request),
                "Category created successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<CategoryResponse>>
    updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {

        return BaseBodyResponse.success(
                categoryService.update(request, id),
                "Category updated successfully"
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<Void>>
    deleteCategory(
            @PathVariable Long id
    ) {

        categoryService.deleteById(id);

        return BaseBodyResponse.success(
                "Category deleted successfully"
        );
    }
}