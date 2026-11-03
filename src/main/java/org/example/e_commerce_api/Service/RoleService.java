package org.example.e_commerce_api.Service;


import org.example.e_commerce_api.DTO.Request.RoleRequest;
import org.example.e_commerce_api.DTO.Response.RoleResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest roleRequest);
    RoleResponse updateRole(Long id, RoleRequest updateRequest);
    RoleResponse getByRoleId(Long roleId);
    List<RoleResponse> getRoleList();
    Page<RoleResponse> filter(Integer page, Integer  pageSize, String filter);
    void deleteRoleById (Long roleId);
    void toggleRoleStatus(Long roleId);
}