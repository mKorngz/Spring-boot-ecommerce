package org.example.e_commerce_api.DTO.Request.Order_Request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotEmpty(message = "Please select at least one cart item")
    private List<Long> cartItemIds;

}