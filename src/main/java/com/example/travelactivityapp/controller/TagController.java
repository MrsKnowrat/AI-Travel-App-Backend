package com.example.travelactivityapp.controller;

// This class handles HTTP requests for tags 

import com.example.travelactivityapp.model.Tag;
import com.example.travelactivityapp.service.TagService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j // Enables logging within the class
@RestController // Marks class as controller; methods return domain object
@Validated // Ensures beans are validated before processing
@CrossOrigin(origins = "*") // Allows c/o requests from all domains
@RequestMapping("/tags") // Maps HTTP requests to handler methods of MVC and REST controllers.
public class TagController {

    @Autowired // Handles business logic for tag-related ops
    TagService tagService;

    // Create Tag
    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) { // Returns response entity with saved tag
        return ResponseEntity.ok(tagService.saveTag(tag)); // Saves tag
    }

    // Get all tags
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags(@Valid @RequestBody Tag tag) { // Returns response entity with list of tags
        return ResponseEntity.ok(tagService.getAllTags()); // Gets all tags
    }

    // Get tag by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@Valid @PathVariable Long id) { // Returns response entity with tag by id
        Optional<Tag> tag = tagService.getTagById(id); // Gets tag by id
        if (tag.isPresent()) { // If tag is present
            return ResponseEntity.ok(tag.get()); // Returns response entity with tag
        } else { // If tag is not present   
            return ResponseEntity.notFound().build(); // Returns response entity with no content
        }
    }

    // Update Tag by ID
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@Valid @PathVariable Long id, @RequestBody Tag tagDetails) { // Returns response entity with updated tag
        return ResponseEntity.ok(tagService.updateTag(id, tagDetails)); // Updates tag
    }

    // Delete Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@Valid @PathVariable Long id) { // Returns response entity with no content
        tagService.deleteTag(id); // Deletes tag
        return ResponseEntity.noContent().build(); // Returns response entity with no content
    }
}
