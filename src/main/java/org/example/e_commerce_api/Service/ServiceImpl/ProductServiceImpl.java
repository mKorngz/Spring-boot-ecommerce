package org.example.e_commerce_api.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Request.Product_Request.CreateProductRequest;
import org.example.e_commerce_api.DTO.Response.ProductResponse;
import org.example.e_commerce_api.Entity.Categories;
import org.example.e_commerce_api.Entity.Products;
import org.example.e_commerce_api.Exception.ResourceNotFoundException;
import org.example.e_commerce_api.Mapper.ProductMapper;
import org.example.e_commerce_api.Repository.CategoryRepository;
import org.example.e_commerce_api.Repository.ProductRepository;
import org.example.e_commerce_api.Service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductResponse findById(Long id) {
        Products products = productRepository.getById(id);
        return ProductMapper.toProductResponse(products);
    }

    @Override
    public ProductResponse save(CreateProductRequest productRequest) {
        Optional<Categories> categoryOptional = categoryRepository.findById(productRequest.getCategoryId());
        if (categoryOptional.isEmpty()){
            throw new ResourceNotFoundException(
                    "Category",
                    productRequest.getCategoryId()
            );

        }
        Categories category = categoryOptional.get();
        Products product = Products.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .categories(category)
                .price(productRequest.getPrice())
                .stock_quantity(productRequest.getStock_quantity())
                .build();
        Products productSaved = productRepository.save(product);
        return ProductMapper.toProductResponse(productSaved);
    }

    @Override
    public ProductResponse updateProduct(CreateProductRequest productRequest, Long id) {
        Products product = getById(id);
        Optional<Categories> categoryOptional = categoryRepository.findById(productRequest.getCategoryId());
        if (categoryOptional.isEmpty()){
            throw new RuntimeException("Category not found");
        }
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock_quantity(productRequest.getStock_quantity());
        product.setCategories(categoryOptional.get());

        product = productRepository.save(product);

        return ProductMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> findAll() {
        List<Products> productList = productRepository.findAll();
        return productList.stream().map(ProductMapper::toProductResponse).toList();
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ProductResponse addStockQty(Long id,Integer qty) {
        if (qty == null || qty <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        Products products = getById(id);
        products.setStock_quantity(products.getStock_quantity()+qty);
        return ProductMapper.toProductResponse(products);
    }

    private Products getById(Long id){
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product", id));
    }
}
