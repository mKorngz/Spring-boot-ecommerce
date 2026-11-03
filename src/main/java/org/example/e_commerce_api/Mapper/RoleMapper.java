package org.example.e_commerce_api.Mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.DTO.Request.RoleRequest;
import org.example.e_commerce_api.DTO.Response.PermissionResponse;
import org.example.e_commerce_api.DTO.Response.RoleResponse;
import org.example.e_commerce_api.Entity.Permissions;
import org.example.e_commerce_api.Entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RoleMapper {
    public static RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .isActive(role.isActive())
                .description(role.getDescription())
                .permission(getPermissionResponse(role.getPermissions()))
                .build();
    }
    public static Role toRoleEntity(RoleRequest roleRequest){
        return Role.builder()
                .name(roleRequest.getName())
                .isActive(roleRequest.isActive())
                .description(roleRequest.getDescription())
                .build();
    }
    private static Set<PermissionResponse> getPermissionResponse(Set<Permissions> permissions){
        return permissions.stream().map(PermissionMapper::toResponse).collect(Collectors.toSet());
    }
}
