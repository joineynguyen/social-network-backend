package com.jn.social_network_backend.service;

import org.springframework.stereotype.Service;
import com.jn.social_network_backend.api.v1.dto.UserRegistrationRequest;
import com.jn.social_network_backend.persistence.entity.User;
import com.jn.social_network_backend.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User registerUser(UserRegistrationRequest req) {
        User user = new User();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());

        User savedUser = userRepository.save(user);

        return savedUser;
    }
}
