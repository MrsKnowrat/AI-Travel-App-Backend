package com.example.travelactivityapp.repository;

// This is the repository interface for the Activity model

import com.example.travelactivityapp.model.Activity;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // This is a Spring annotation that marks the interface as a repository
public interface IActivityRepository extends JpaRepository<Activity, Long> { 
}
