package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Tag;
import com.example.travelactivityapp.repository.ITagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class TagService {

    @Autowired
    ITagRepository tagRepository;

    /* CRUD
    C- Included here
    R- Included here
    U- Included here
    D- Included here
    */

    // Create Tag

    // Get all tags
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // Get Tag by ID
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    // Update/Save Tag -- does it make sense to keep this method since the one below has a save?
    // Should save be on all of these methods?
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Update Tag by ID
    public Tag updateTag(Long id, Tag tagDetails) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));

        tag.setTagName(tagDetails.getTagName());

        return tagRepository.save(tag);
    }

    // Delete Tag
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }
}
