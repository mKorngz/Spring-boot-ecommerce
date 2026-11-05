package org.example.e_commerce_api.Service;

import org.example.e_commerce_api.DTO.Request.Product_Request.CreateProductRequest;
import org.example.e_commerce_api.DTO.Response.ProductResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface ProductService {
    ProductResponse findById(Long id);
    ProductResponse save(CreateProductRequest productRequest);
    ProductResponse updateProduct(CreateProductRequest productRequest, Long id);
    List<ProductResponse> findAll();
    void deleteById(Long id);
    ProductResponse addStockQty(Long id,Integer qty);
}
