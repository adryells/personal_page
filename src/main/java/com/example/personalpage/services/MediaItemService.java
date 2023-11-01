package com.example.personalpage.services;

import com.example.personalpage.dtos.MediaItemRecordDTO;
import com.example.personalpage.models.MediaItemModel;
import com.example.personalpage.repositories.MediaItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaItemService {
    @Autowired
    MediaItemRepository mediaItemRepository;

    public Optional<MediaItemModel> getMediaItemById(UUID id){
        return mediaItemRepository.findById(id);
    }

    public List<MediaItemModel> getMediaItems(int page, int perPage){
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);
        Page<MediaItemModel> mediaItemPage = mediaItemRepository.findAll(pageRequest);

        return mediaItemPage.getContent();
    }


    public void createMediaItem(MediaItemModel mediaItemModel) {
        mediaItemRepository.save(mediaItemModel);
    }


    public MediaItemModel updateMediaItem(UUID id, MediaItemRecordDTO mediaItemData) {
        Optional<MediaItemModel> mediaItemModel = this.getMediaItemById(id);

        if (mediaItemModel.isPresent()) {
            MediaItemModel mediaItem = mediaItemModel.get();

            if (mediaItemData.url() != null) {
                mediaItem.setTitle(mediaItemData.url());
            }

            mediaItemRepository.save(mediaItem);

            return mediaItem;

        } else {
            return null;
        }
    }

    public void deleteMediaItem(UUID id){
        Optional<MediaItemModel> mediaItemModel = this.getMediaItemById(id);

        if (mediaItemModel.isPresent()){
            MediaItemModel mediaItem = mediaItemModel.get();
            mediaItemRepository.delete(mediaItem);
        }
    }
}
