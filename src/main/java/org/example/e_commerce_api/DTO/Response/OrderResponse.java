package org.example.e_commerce_api.DTO.Response;

import lombok.*;
import org.example.e_commerce_api.Constant.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    private String order_number;

    private Long userId;

    private Double total_amount;

    private OrderStatusEnum status;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}
