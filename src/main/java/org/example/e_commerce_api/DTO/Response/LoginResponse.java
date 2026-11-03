package org.example.e_commerce_api.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresInMs;
    private String email;
    private String fullName;
}
