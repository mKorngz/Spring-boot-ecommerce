package org.example.e_commerce_api.Config;

import lombok.RequiredArgsConstructor;
import org.example.e_commerce_api.Config.JWT.JwtService;
import org.example.e_commerce_api.DTO.Request.LoginRequest;
import org.example.e_commerce_api.DTO.Request.RegisterRequest;
import org.example.e_commerce_api.DTO.Response.LoginResponse;
import org.example.e_commerce_api.DTO.Response.MeResponse;
import org.example.e_commerce_api.DTO.Response.RegisterResponse;
import org.example.e_commerce_api.Entity.Carts;
import org.example.e_commerce_api.Entity.Role;
import org.example.e_commerce_api.Entity.Users;
import org.example.e_commerce_api.Exception.ResourceNotFoundException;
import org.example.e_commerce_api.Exception.UnauthorizedException;
import org.example.e_commerce_api.Mapper.RoleMapper;
import org.example.e_commerce_api.Repository.CartRepository;
import org.example.e_commerce_api.Repository.RoleRepository;
import org.example.e_commerce_api.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail()
                    , loginRequest.getPassword()));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException("Username or password is incorrect");
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userDetails);


        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType("Bearer")
                .expiresInMs(jwtService.getExpiresInMs())
                .email(userDetails.getUsername())
                .fullName(userDetails.getFullName())
                .build();
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        Role normalUserRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));
        Users user = Users.builder()
                .fullName(registerRequest.getFullName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .enabled(registerRequest.isEnabled())
                .roles(Set.of(normalUserRole))
                .build();
        user = userRepository.save(user);

        return RegisterResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roleResponses(Set.of(RoleMapper.toRoleResponse(normalUserRole)))
                .build();
    }

    @Override
    public MeResponse me() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return MeResponse.builder()
                .id(userDetails.getId())
                .fullName(userDetails.getFullName())
                .email(userDetails.getUsername())
                .build();
    }
}
