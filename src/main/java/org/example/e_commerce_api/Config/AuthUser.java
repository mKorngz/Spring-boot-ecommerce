package org.example.e_commerce_api.Config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@Setter
@Builder
public class AuthUser {
    private Long id;
    private String email;
    private String password;
    private Set<GrantedAuthority> authorities;
    private boolean enabled;
    private String fullName;
}
