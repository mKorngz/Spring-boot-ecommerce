package org.example.e_commerce_api.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Request.Order_Request.CreateOrderRequest;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.OrderResponse;
import org.example.e_commerce_api.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<BaseBodyResponse<OrderResponse>>
    createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        return BaseBodyResponse.success(
                orderService.createOrder(request),
                "Order created successfully"
        );
    }

    @GetMapping("/my-orders")
    public ResponseEntity<BaseBodyResponse<List<OrderResponse>>>
    getMyOrders() {

        return BaseBodyResponse.success(
                orderService.getMyOrders(),
                "Orders retrieved successfully"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseBodyResponse<OrderResponse>>
    getOrderById(@PathVariable Long id) {

        return BaseBodyResponse.success(
                orderService.getOrderById(id),
                "Order retrieved successfully"
        );
    }
}