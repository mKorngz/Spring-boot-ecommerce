package org.example.e_commerce_api.DTO.Request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private boolean accountNonLocked;
}
