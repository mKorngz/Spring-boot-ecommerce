package org.example.e_commerce_api.Controller;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.ProductResponse;
import org.example.e_commerce_api.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<ProductResponse>>>
    findAll() {

        return BaseBodyResponse.success(
                productService.findAll(),
                "Products retrieved successfully"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<ProductResponse>>
    findById(
            @PathVariable Long id
    ) {

        return BaseBodyResponse.success(
                productService.findById(id),
                "Product retrieved successfully"
        );
    }
}