package org.example.e_commerce_api.DTO.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private Long expiresInMs;
    private String email;
    private String fullName;
}
