package com.example.travelactivityapp.repository;

import com.example.travelactivityapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITagRepository extends JpaRepository<Tag, Integer> {
}
