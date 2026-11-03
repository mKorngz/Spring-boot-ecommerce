package org.example.e_commerce_api.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleRequest {
    private String name; // e.g., "ROLE_ADMIN", "ROLE_HR_MANAGER", "ROLE_EMPLOYEE"
    private boolean isActive;
    private String description;
    private Set<Long> permissionIds;
}
