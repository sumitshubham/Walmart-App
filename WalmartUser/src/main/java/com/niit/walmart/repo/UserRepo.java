package com.niit.walmart.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.niit.walmart.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    void deleteById(Long id);

    User findByUsername (String username);
    Optional<User> findById (Long id);
}