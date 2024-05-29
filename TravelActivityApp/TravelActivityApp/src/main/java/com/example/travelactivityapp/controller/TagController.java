package com.example.travelactivityapp.controller;

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

@Slf4j
@RestController
@RequestMapping("/tags")
@Validated
@CrossOrigin(origins = "*")
public class TagController {

    @Autowired
    TagService tagService;

    /* CRUD
    C- Included here
    R- Included here
    U- Included here
    D- Included here
    */

    // Create Tag
    @PostMapping
    public ResponseEntity<Tag> createTag(@Valid @RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.saveTag(tag));
    }

    // Get all tags
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    // Get tag by ID
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        Optional<Tag> tag = tagService.getTagById(id);
        if (tag.isPresent()) {
            return ResponseEntity.ok(tag.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update/Save Tag -- does it make sense to keep this method since the one below has a save on TagService?
    // Should save be on all of these methods?


    // Update Tag by ID
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tagDetails) {
        return ResponseEntity.ok(tagService.updateTag(id, tagDetails));
    }

    // Delete Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
