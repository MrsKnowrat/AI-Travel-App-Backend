package com.example.travelactivityapp.repository;

// This is the repository interface for the Tag model   

import com.example.travelactivityapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // This is a Spring annotation that marks the interface as a repository
public interface ITagRepository extends JpaRepository<Tag, Long> {
}
