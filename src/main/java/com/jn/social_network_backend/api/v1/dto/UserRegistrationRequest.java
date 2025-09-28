package com.jn.social_network_backend.api.v1.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user registration request. Only first name and last name are required.
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRegistrationRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name too long")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name too long")
    private String lastName;
}
