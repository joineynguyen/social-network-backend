package com.jn.social_network_backend.api.v1.dto;

import com.jn.social_network_backend.domain.RegistrationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponse {

    private String firstName;
    private String lastName;
    private RegistrationStatus status;
}
