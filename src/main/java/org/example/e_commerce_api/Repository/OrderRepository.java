package org.example.e_commerce_api.Repository;

import org.example.e_commerce_api.Constant.OrderStatusEnum;
import org.example.e_commerce_api.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUsersId(Long userId);
    long countByOrderStatusEnum(OrderStatusEnum status);
}
