package dev.adryell.personalpage.controllers;

import dev.adryell.personalpage.dtos.TagDTO;
import dev.adryell.personalpage.dtos.UpdateTagDTO;
import dev.adryell.personalpage.models.Tag;
import dev.adryell.personalpage.repositories.TagRepository;
import dev.adryell.personalpage.services.RequiresPermission;
import dev.adryell.personalpage.utils.enums.Permissions;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @RequiresPermission(Permissions.CREATE_TAG)
    @PostMapping("/create")
    public ResponseEntity<Object> createTag(@RequestBody @Valid TagDTO tagData) {
        Tag tag = new Tag();

        String slug = tagData.name().replace(" ", "_").toLowerCase();
        if (tagRepository.findBySlug(slug) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    String.format("Tag %s already exists.", tagData.name())
            );
        }

        tag.setName(tagData.name());
        tag.setSlug(slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(tagRepository.save(tag));
    }

    @RequiresPermission(Permissions.UPDATE_TAG)
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTag(@PathVariable Long id, @RequestBody @Valid UpdateTagDTO tagData) {
        Optional<Tag> existingTag = tagRepository.findById(id);

        if (existingTag.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found.");
        }

        Tag updatingTag = existingTag.get();

        if (tagData.name() != null) {
            String slug = tagData.name().replace(" ", "_").toLowerCase();
            if (!slug.equals(updatingTag.getSlug()) && tagRepository.findBySlug(slug) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        String.format("Tag with name %s already exists.", tagData.name())
                );
            }
            updatingTag.setName(tagData.name());
            updatingTag.setSlug(slug);
        }
        if (tagData.active() != null) {
            updatingTag.setActive(tagData.active());
        }

        return ResponseEntity.ok(tagRepository.save(updatingTag));
    }

    @RequiresPermission(Permissions.DELETE_TAG)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable Long id) {
        Optional<Tag> existingTag = tagRepository.findById(id);

        if (existingTag.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found.");
        }

        tagRepository.delete(existingTag.get());

        return ResponseEntity.ok("Deleted successful.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTagById(@PathVariable Long id) {
        Optional<Tag> existingTag = tagRepository.findById(id);

        return existingTag.
                <ResponseEntity<Object>>map(tag -> ResponseEntity.status(HttpStatus.OK).body(tag))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found."));
    }

    @GetMapping("/")
    public Page<Tag> getTags(@RequestParam(required = false) Boolean active, Pageable pageable) {
        if (active != null) {
            return tagRepository.findByActive(active, pageable);
        } else {
            return tagRepository.findAll(pageable);
        }
    }
}
