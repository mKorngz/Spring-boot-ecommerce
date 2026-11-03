package org.example.e_commerce_api.DTO.Response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MeResponse {
    private Long id;
    private String fullName;
    private String email;
}