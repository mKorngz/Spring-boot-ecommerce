package org.example.e_commerce_api.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.DTO.Request.CategoryRequest;
import org.example.e_commerce_api.DTO.Response.CategoryResponse;
import org.example.e_commerce_api.Entity.Categories;
import org.example.e_commerce_api.Exception.ResourceNotFoundException;
import org.example.e_commerce_api.Mapper.CategoryMapper;
import org.example.e_commerce_api.Repository.CategoryRepository;
import org.example.e_commerce_api.Repository.ProductRepository;
import org.example.e_commerce_api.Service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Categories categories = Categories.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .build();
        Categories savedCategory =
                categoryRepository.save(categories);

        return CategoryMapper.toCategoryResponse(savedCategory);
    }

    @Transactional
    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, Long id) {
        Categories category = getById(id);
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        return CategoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Categories category = getById(id);
        return CategoryMapper.
                toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(CategoryMapper::toCategoryResponse).toList();
    }

    @Override
    public void deleteById(Long id) {
        Categories category = getById(id);

        categoryRepository.delete(category);
    }

    private Categories getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}
