package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Entity.Order_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<Order_items,Long> {
    List<Order_items> findByOrdersId(Long orderId);
}
