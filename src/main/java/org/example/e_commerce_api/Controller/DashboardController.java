package org.example.e_commerce_api.Controller;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.DashboardResponse;
import org.example.e_commerce_api.Service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<BaseBodyResponse<DashboardResponse>>
    getDashboard() {

        return BaseBodyResponse.success(
                dashboardService.getDashboard(),
                "Dashboard retrieved successfully"
        );
    }
}