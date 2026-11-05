package org.example.e_commerce_api.Service;

import org.example.e_commerce_api.DTO.Request.Order_Request.CreateOrderRequest;
import org.example.e_commerce_api.DTO.Request.Order_Request.UpdateOrderStatusRequest;
import org.example.e_commerce_api.DTO.Response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getMyOrders();

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    OrderResponse updateOrderStatus(
            Long id,
            UpdateOrderStatusRequest request);

}