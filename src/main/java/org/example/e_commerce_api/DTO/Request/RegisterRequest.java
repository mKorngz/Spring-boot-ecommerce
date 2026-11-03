package org.example.e_commerce_api.DTO.Request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountNonLocked;
}
