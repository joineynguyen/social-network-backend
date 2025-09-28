package com.jn.social_network_backend.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jn.social_network_backend.persistence.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}