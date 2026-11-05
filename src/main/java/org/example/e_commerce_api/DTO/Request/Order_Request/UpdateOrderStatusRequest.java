package org.example.e_commerce_api.DTO.Request.Order_Request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.e_commerce_api.Constant.OrderStatusEnum;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    @NotNull(message = "Status is required")
    private OrderStatusEnum status;
}