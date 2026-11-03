package org.example.e_commerce_api.Mapper;



import org.example.e_commerce_api.DTO.Response.RoleResponse;
import org.example.e_commerce_api.DTO.Response.UserResponse;
import org.example.e_commerce_api.Entity.Role;
import org.example.e_commerce_api.Entity.Users;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getFullName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .role(getRoleResponse(user.getRoles()))
                .build();
    }
    private static Set<RoleResponse> getRoleResponse(Set<Role> roles){
        return roles.stream().map(RoleMapper::toRoleResponse).collect(Collectors.toSet());
    }
}