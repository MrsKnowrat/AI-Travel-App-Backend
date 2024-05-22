package com.example.travelactivityapp.controller;

import com.example.travelactivityapp.model.Tag;
import com.example.travelactivityapp.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Integer id) {
        Optional<Tag> tag = tagService.getTagById(id);
        if (tag.isPresent()) {
            return ResponseEntity.ok(tag.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.saveTag(tag));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Integer id, @RequestBody Tag tagDetails) {
        return ResponseEntity.ok(tagService.updateTag(id, tagDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
