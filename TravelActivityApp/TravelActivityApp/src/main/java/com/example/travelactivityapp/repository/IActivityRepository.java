package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivityRepository extends JpaRepository<Activity, Integer> {
}
