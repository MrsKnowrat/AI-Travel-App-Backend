package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // This is a Spring annotation that marks the interface as a repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username); // This method finds the user by username

    Optional<User> findUserByEmail(String email); // This method finds the user by email
}
