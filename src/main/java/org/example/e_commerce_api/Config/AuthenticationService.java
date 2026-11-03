package org.example.e_commerce_api.Config;

import org.example.e_commerce_api.DTO.Request.LoginRequest;
import org.example.e_commerce_api.DTO.Request.RegisterRequest;
import org.example.e_commerce_api.DTO.Response.LoginResponse;
import org.example.e_commerce_api.DTO.Response.MeResponse;
import org.example.e_commerce_api.DTO.Response.RegisterResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest loginRequest);
    RegisterResponse register(RegisterRequest registerRequest);
    MeResponse me();
}
