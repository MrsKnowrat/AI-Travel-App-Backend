package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProfileRepository extends JpaRepository<Profile, Integer> {
    //List<Profile> findProfileByUserId(Integer userId);

    List<Profile> getProfileByUserUserId(Integer userId);
}
