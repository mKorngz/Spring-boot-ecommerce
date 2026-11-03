package org.example.e_commerce_api.Service.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.DTO.Request.RoleRequest;
import org.example.e_commerce_api.DTO.Response.RoleResponse;
import org.example.e_commerce_api.Entity.Permissions;
import org.example.e_commerce_api.Entity.Role;
import org.example.e_commerce_api.Exception.ResourceNotFoundException;
import org.example.e_commerce_api.Mapper.RoleMapper;
import org.example.e_commerce_api.Repository.PermissionRepository;
import org.example.e_commerce_api.Repository.RoleRepository;
import org.example.e_commerce_api.Service.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Permission;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional
    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = RoleMapper.toRoleEntity(roleRequest);
        List<Permissions> permissionList = permissionRepository.findAllById(roleRequest.getPermissionIds());
        role.setPermissions(new HashSet<>(permissionList));
        role = roleRepository.save(role);
        return RoleMapper.toRoleResponse(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RoleResponse updateRole(Long id, RoleRequest updateRequest) {
        Role existing = getById(id);

        existing.setName(updateRequest.getName());
        existing.setDescription(updateRequest.getDescription());

        existing.getPermissions().clear();
        List<Permissions> permissionList = permissionRepository.findAllById(updateRequest.getPermissionIds());
        existing.setPermissions(new HashSet<>(permissionList));
        existing = roleRepository.save(existing);
        return RoleMapper.toRoleResponse(existing);
    }

    @Transactional(readOnly = true)
    @Override
    public RoleResponse getByRoleId(Long roleId) {
        return RoleMapper.toRoleResponse(getById(roleId));
    }

    @Transactional
    @Override
    public List<RoleResponse> getRoleList() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(RoleMapper::toRoleResponse).toList();
    }

    @Override
    public Page<RoleResponse> filter(Integer page, Integer pageSize, String filter) {
        Pageable pageable = PageRequest.of(page-1,pageSize);
        Page<Role> result = roleRepository.findByNameContainingIgnoreCase(filter,pageable);
        return result.map(RoleMapper::toRoleResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    @Transactional
    @Override
    public void toggleRoleStatus(Long roleId) {
        Role role = getById(roleId);
        role.setActive(!role.isActive());
        roleRepository.save(role);
    }

    private Role getById(Long id){
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}