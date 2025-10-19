package com.jn.social_network_backend.api.v1.mapper;

import org.springframework.stereotype.Component;

import com.jn.social_network_backend.api.v1.dto.UserRegistrationResponse;
import com.jn.social_network_backend.domain.RegistrationStatus;
import com.jn.social_network_backend.persistence.entity.User;

@Component
public class UserMapper {
    public UserRegistrationResponse toUserRegistrationDto(User user) {
        return UserRegistrationResponse.builder()
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .status(RegistrationStatus.SUCCESS)
            .build();
    }
}
