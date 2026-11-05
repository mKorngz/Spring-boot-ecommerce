package org.example.e_commerce_api.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Request.Order_Request.UpdateOrderStatusRequest;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.OrderResponse;
import org.example.e_commerce_api.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<BaseBodyResponse<List<OrderResponse>>>
    getAllOrders() {

        return BaseBodyResponse.success(
                orderService.getAllOrders(),
                "Orders retrieved successfully"
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<BaseBodyResponse<OrderResponse>>
    updateOrderStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusRequest request
    ) {

        return BaseBodyResponse.success(
                orderService.updateOrderStatus(id, request),
                "Order status updated successfully"
        );
    }
}
