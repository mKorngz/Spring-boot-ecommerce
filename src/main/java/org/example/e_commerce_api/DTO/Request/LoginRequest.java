package org.example.e_commerce_api.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "Email must be not null")
    private String email;
    @NotBlank(message = "Password must not be blank")
    @Size(min = 4, message = "Password must be at least 4 digits")
    private String password;
}
