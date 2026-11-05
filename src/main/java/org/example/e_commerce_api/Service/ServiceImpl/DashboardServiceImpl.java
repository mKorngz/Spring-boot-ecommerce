package org.example.e_commerce_api.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.Constant.OrderStatusEnum;
import org.example.e_commerce_api.DTO.Response.DashboardResponse;
import org.example.e_commerce_api.Entity.Orders;
import org.example.e_commerce_api.Repository.OrderRepository;
import org.example.e_commerce_api.Repository.ProductRepository;
import org.example.e_commerce_api.Repository.UserRepository;
import org.example.e_commerce_api.Service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public DashboardResponse getDashboard() {

        long totalUsers =
                userRepository.count();

        long totalProducts =
                productRepository.count();

        long totalOrders =
                orderRepository.count();

        long pendingOrders =
                orderRepository.countByOrderStatusEnum(
                        OrderStatusEnum.PENDING);

        double totalRevenue =
                orderRepository.findAll()
                        .stream()
                        .filter(order ->
                                order.getOrderStatusEnum()
                                        == OrderStatusEnum.COMPLETED)
                        .mapToDouble(Orders::getTotal_amount)
                        .sum();

        return DashboardResponse.builder()
                .totalUsers(totalUsers)
                .totalProducts(totalProducts)
                .totalOrders(totalOrders)
                .pendingOrders(pendingOrders)
                .totalRevenue(totalRevenue)
                .build();
    }
}