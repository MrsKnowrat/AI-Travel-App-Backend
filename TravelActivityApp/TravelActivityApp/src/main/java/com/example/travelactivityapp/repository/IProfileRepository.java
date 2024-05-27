package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProfileRepository extends JpaRepository<Profile, Long> {

    List<Profile> getProfileByUserId(Long id);

    //List<Profile> getProfileByUserUserId(Long id);
}
