package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProfileRepository extends JpaRepository<Profile, Integer> {
}
