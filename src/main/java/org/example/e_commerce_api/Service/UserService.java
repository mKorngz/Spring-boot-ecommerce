package org.example.e_commerce_api.Service;

import org.example.e_commerce_api.DTO.Request.RegisterRequest;
import org.example.e_commerce_api.DTO.Request.UserRequest;
import org.example.e_commerce_api.DTO.Response.RegisterResponse;
import org.example.e_commerce_api.DTO.Response.UserResponse;

import java.util.List;

public interface UserService {
    RegisterResponse register(RegisterRequest registerRequest);
    List<UserResponse> findAll();
    UserResponse updateUser(Long id, UserRequest userRequest);
    void deleteById(Long id);
    UserResponse findById(Long id);
}
