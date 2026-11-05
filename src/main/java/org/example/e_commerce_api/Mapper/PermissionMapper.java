package org.example.e_commerce_api.Mapper;


import org.example.e_commerce_api.DTO.Response.PermissionResponse;
import org.example.e_commerce_api.Entity.Permissions;

public class PermissionMapper {
    public static PermissionResponse toResponse(Permissions permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName().getAuthority())
                .category(permission.getCategory())
                .description(permission.getDescription())
                .build();
    }
}
