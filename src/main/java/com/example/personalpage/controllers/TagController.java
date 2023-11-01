package com.example.personalpage.controllers;

import com.example.personalpage.dtos.TagRecordDTO;
import com.example.personalpage.models.TagModel;
import com.example.personalpage.services.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/")
    public ResponseEntity<List<TagModel>> getTags(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        List<TagModel> tags = tagService.getTags(page, perPage);

        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Object>> getTagById(@PathVariable(name = "id") UUID id){
        Optional<TagModel> tag = tagService.getTagById(id);

        if (tag.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.of("Tag not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(tag));
    }

    @PostMapping("/")
    public ResponseEntity<TagModel> createTag(@RequestBody @Valid TagRecordDTO tagData){
        TagModel tagModel = new TagModel();

        BeanUtils.copyProperties(tagData, tagModel);
        tagService.createTag(tagModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(tagModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagModel> updateTag(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid TagRecordDTO tagData
    ){
        TagModel tagModel = tagService.updateTag(id, tagData);

        return ResponseEntity.status(HttpStatus.OK).body(tagModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable(name="id") UUID id){
        tagService.deleteTag(id);

        return ResponseEntity.status(HttpStatus.OK).body("Tag deleted successfully!");
    }

}
