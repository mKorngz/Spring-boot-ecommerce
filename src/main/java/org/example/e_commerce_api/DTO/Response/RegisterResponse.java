package org.example.e_commerce_api.DTO.Response;


import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
    private Set<RoleResponse> roleResponses;

}
