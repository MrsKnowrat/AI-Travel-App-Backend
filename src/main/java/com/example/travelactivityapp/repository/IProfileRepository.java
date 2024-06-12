package com.example.travelactivityapp.repository;

// This is the repository interface for the Profile model

import com.example.travelactivityapp.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // This is a Spring annotation that marks the interface as a repository
public interface IProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> getProfileByUserId(Long id); // This method finds the profile by user id

    
}
