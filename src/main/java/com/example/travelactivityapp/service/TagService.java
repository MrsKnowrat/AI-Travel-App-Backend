package com.example.travelactivityapp.service;

// This service layer class is responsible for handling the business logic for the Tag entity, including CRUD

import com.example.travelactivityapp.model.Tag;
import com.example.travelactivityapp.repository.ITagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j // Lombok annotation to generate a logger
@Service // This class is a service
@Transactional // This class is transactional
public class TagService {

    @Autowired
    ITagRepository tagRepository; // This is the repository layer class for the Tag entity


    // Create Tag - coming soon!

    // Get all tags
    public List<Tag> getAllTags() {
        return tagRepository.findAll(); 
    }

    // Get Tag by ID
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    // Update/Save Tag 
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Update Tag by ID
    public Tag updateTag(Long id, Tag tagDetails) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found")); // Find the tag by ID    

        tag.setTagName(tagDetails.getTagName()); // Set the new tag name

        return tagRepository.save(tag); // Save the tag
    }

    // Delete Tag
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
