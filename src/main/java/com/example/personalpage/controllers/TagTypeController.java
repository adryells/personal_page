package com.example.personalpage.controllers;

import com.example.personalpage.dtos.TagTypeRecordDTO;
import com.example.personalpage.models.TagTypeModel;
import com.example.personalpage.services.TagTypeService;
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
@RequestMapping("/tagtype")
public class TagTypeController {
    @Autowired
    TagTypeService tagTypeService;

    @GetMapping("/")
    public ResponseEntity<List<TagTypeModel>> getTagTypes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        List<TagTypeModel> tags = tagTypeService.getTagTypes(page, perPage);

        return ResponseEntity.status(HttpStatus.OK).body(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Object>> getTagTypeById(@PathVariable(name = "id") UUID id){
        Optional<TagTypeModel> tag = Optional.ofNullable(tagTypeService.getTagTypeById(id));

        if (tag.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.of("TagType not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(tag));
    }

    @PostMapping("/")
    public ResponseEntity<TagTypeModel> createTagType(@RequestBody @Valid TagTypeRecordDTO tagData){
        TagTypeModel tagModel = new TagTypeModel();

        BeanUtils.copyProperties(tagData, tagModel);
        tagTypeService.createTagType(tagModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(tagModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTagType(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid TagTypeRecordDTO tagData
    ){
        TagTypeModel tagModel = tagTypeService.updateTagType(id, tagData);

        if (tagModel == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag Type not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(tagModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTagType(@PathVariable(name="id") UUID id){
        boolean wasDeleted = tagTypeService.deleteTagType(id);

        if (!wasDeleted){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TagType not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("TagType deleted successfully!");
    }
}
