package org.example.e_commerce_api.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.DTO.Request.Product_Request.CreateProductRequest;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.ProductResponse;
import org.example.e_commerce_api.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseBodyResponse<ProductResponse>>
    createProduct(
            @Valid @RequestBody CreateProductRequest request
    ) {
        log.info("{}", request);

        return BaseBodyResponse.success(
                productService.save(request),
                "Product created successfully"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<ProductResponse>>
    updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest request
    ) {

        return BaseBodyResponse.success(
                productService.updateProduct(request, id),
                "Product updated successfully"
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<Void>>
    deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteById(id);

        return BaseBodyResponse.success(
                "Product deleted successfully"
        );
    }
}