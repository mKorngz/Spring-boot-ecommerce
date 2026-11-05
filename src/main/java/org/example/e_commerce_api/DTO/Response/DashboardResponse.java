package org.example.e_commerce_api.DTO.Response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {

    private Long totalUsers;

    private Long totalProducts;

    private Long totalOrders;

    private Long pendingOrders;

    private Double totalRevenue;
}