package org.example.e_commerce_api.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class RoleResponse {
    private Long id;
    private String name; // e.g., "ROLE_ADMIN", "ROLE_HR_MANAGER", "ROLE_EMPLOYEE"
    private boolean isActive;
    private String description;
    private Set<PermissionResponse> permission;
}
