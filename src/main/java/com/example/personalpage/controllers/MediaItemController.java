package com.example.personalpage.controllers;

import com.example.personalpage.dtos.MediaItemRecordDTO;
import com.example.personalpage.models.MediaItemModel;
import com.example.personalpage.services.MediaItemService;
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
@RequestMapping("/mediaitem")
public class MediaItemController {
    @Autowired
    MediaItemService mediaItemService;

    @GetMapping("/")
    public ResponseEntity<List<MediaItemModel>> getMediaItems(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        List<MediaItemModel> mediaItems = mediaItemService.getMediaItems(page, perPage);

        return ResponseEntity.status(HttpStatus.OK).body(mediaItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Object>> getMediaItemById(@PathVariable(name = "id") UUID id){
        Optional<MediaItemModel> mediaItem = mediaItemService.getMediaItemById(id);

        if (mediaItem.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.of("MediaItem not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(mediaItem));
    }

    @PostMapping("/")
    public ResponseEntity<MediaItemModel> createMediaItem(@RequestBody @Valid MediaItemRecordDTO mediaItemData){
        MediaItemModel mediaItemModel = new MediaItemModel();

        BeanUtils.copyProperties(mediaItemData, mediaItemModel);
        mediaItemService.createMediaItem(mediaItemModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(mediaItemModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaItemModel> updateMediaItem(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid MediaItemRecordDTO mediaItemData
    ){
        MediaItemModel mediaItemModel = mediaItemService.updateMediaItem(id, mediaItemData);

        return ResponseEntity.status(HttpStatus.OK).body(mediaItemModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMediaItem(@PathVariable(name="id") UUID id){
        mediaItemService.deleteMediaItem(id);

        return ResponseEntity.status(HttpStatus.OK).body("MediaItem deleted successfully!");
    }
}
