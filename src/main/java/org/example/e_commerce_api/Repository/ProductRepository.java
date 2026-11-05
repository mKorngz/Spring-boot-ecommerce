package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    Optional<Products> findByNameIgnoreCase(String name);
    List<Products> findByCategories_Id(Long categoryId);
}
