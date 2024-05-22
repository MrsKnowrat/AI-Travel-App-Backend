package com.example.travelactivityapp.service;

import com.example.travelactivityapp.model.Tag;
import com.example.travelactivityapp.repository.ITagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TagService {
    @Autowired
    private ITagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Integer id) {
        return tagRepository.findById(id);
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public void deleteTag(Integer id) {
        tagRepository.deleteById(id);
    }

    public Tag updateTag(Integer id, Tag tagDetails) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));

        tag.setName(tagDetails.getName());

        return tagRepository.save(tag);
    }
}
