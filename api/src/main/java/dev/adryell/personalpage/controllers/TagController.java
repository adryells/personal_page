package dev.adryell.personalpage.controllers;

import dev.adryell.personalpage.dtos.TagDTO;
import dev.adryell.personalpage.dtos.UpdateTagDTO;
import dev.adryell.personalpage.models.Media;
import dev.adryell.personalpage.models.Project;
import dev.adryell.personalpage.models.Tag;
import dev.adryell.personalpage.projections.ProjectProjection;
import dev.adryell.personalpage.projections.TagProjection;
import dev.adryell.personalpage.repositories.MediaRepository;
import dev.adryell.personalpage.repositories.TagRepository;
import dev.adryell.personalpage.services.RequiresPermission;
import dev.adryell.personalpage.utils.enums.MediaContentTypes;
import dev.adryell.personalpage.utils.enums.Permissions;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    MediaRepository mediaRepository;

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

        try {
            tag.setMedias(new HashSet<>());
            updateTagMedias(tagData.iconId(), tag);
        } catch (ResponseStatusException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
        }

        tagRepository.save(tag);

        return ResponseEntity.status(HttpStatus.CREATED).body(createTagProjection(tag));
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

        try {
            if (tagData.iconId() != null) {
                updateTagMedias(tagData.iconId(), updatingTag);
            }
        } catch (ResponseStatusException exception) {
            return ResponseEntity.status(exception.getStatusCode()).body(exception.getMessage());
        }

        tagRepository.save(updatingTag);

        return ResponseEntity.status(HttpStatus.OK).body(createTagProjection(updatingTag));
    }

    private void updateTagMedias(Long iconId, Tag tag) {
        if (iconId != null) {
            Media new_tag_icon = mediaRepository.findById(iconId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag icon not found."));

            if (!new_tag_icon.getMediaContentType().getSlug().equalsIgnoreCase(MediaContentTypes.TAG_ICON.toString())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Media must be a tag icon.");
            }
            removeMediaByContentType(tag, MediaContentTypes.TAG_ICON);
            tag.getMedias().add(new_tag_icon);
        }
    }

    private void removeMediaByContentType(Tag tag, MediaContentTypes contentType) {
        if (tag.getMedias() != null) {
            tag.getMedias().removeIf(
                    media -> media.getMediaContentType().getSlug().equalsIgnoreCase(contentType.toString())
            );
        }
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
                <ResponseEntity<Object>>map(tag -> ResponseEntity.status(HttpStatus.OK).body(createTagProjection(tag)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found."));
    }

    @GetMapping("/")
    public Page<TagProjection> getTags(@RequestParam(required = false) Boolean active, Pageable pageable) {
        Page<Tag> tags;

        if (active != null) {
            tags = tagRepository.findByActive(active, pageable);
        } else {
            tags = tagRepository.findAll(pageable);
        }

        return mapTagsToProjection(tags);
    }

    private Page<TagProjection> mapTagsToProjection(Page<Tag> tags) {
        List<TagProjection> projections = new ArrayList<>();
        for (Tag tag : tags.getContent()) {
            projections.add(this.createTagProjection(tag));
        }
        return new PageImpl<>(projections, tags.getPageable(), tags.getTotalElements());
    }

    private TagProjection createTagProjection(Tag tag) {
        return new TagProjectionImpl(
                tag.getId(),
                tag.getName(),
                tag.getSlug(),
                tag.isActive(),
                tag.getIcon() != null ? tag.getIcon().getURL() : null
        );
    }

    private record TagProjectionImpl(
            Long id,
            String name,
            String slug,
            Boolean active,
            String iconURL
    ) implements TagProjection {
    }
}
