package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Categories,Long> {
    Optional<Categories> findByName(String name);
}
