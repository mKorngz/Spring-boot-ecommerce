package org.example.e_commerce_api.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.Config.AuthenticationService;
import org.example.e_commerce_api.DTO.Request.LoginRequest;
import org.example.e_commerce_api.DTO.Request.RegisterRequest;
import org.example.e_commerce_api.DTO.Response.BasebodyResponse.BaseBodyResponse;
import org.example.e_commerce_api.DTO.Response.LoginResponse;
import org.example.e_commerce_api.DTO.Response.MeResponse;
import org.example.e_commerce_api.DTO.Response.RegisterResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Object login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response =
                authenticationService.login(request);

        return BaseBodyResponse.success(
                response,
                "Login successful"
        );
    }

    @PostMapping("/register")
    public Object register(
            @Valid @RequestBody RegisterRequest request) {

        RegisterResponse response =
                authenticationService.register(request);

        return BaseBodyResponse.success(
                response,
                "Register successful"
        );
    }

    @GetMapping("/me")
    public Object me() {

        MeResponse response =
                authenticationService.me();

        return BaseBodyResponse.success(
                response,
                "Current user retrieved successfully"
        );
    }
}