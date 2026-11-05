package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Cart_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<Cart_items, Long> {
    Optional<Cart_items> findByCartsIdAndProductsId(Long cartId, Long productId);
    List<Cart_items> findAllByIdIn(List<Long> ids);
    List<Cart_items> findByCartsId(Long cartId);
}
