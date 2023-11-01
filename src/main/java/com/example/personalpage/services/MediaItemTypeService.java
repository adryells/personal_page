package com.example.personalpage.services;

import com.example.personalpage.dtos.MediaItemTypeRecordDTO;
import com.example.personalpage.models.MediaItemTypeModel;
import com.example.personalpage.repositories.MediaItemTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaItemTypeService {
    @Autowired
    MediaItemTypeRepository mediaItemTypeRepository;

    public Optional<MediaItemTypeModel> getMediaItemTypeById(UUID id){
        return mediaItemTypeRepository.findById(id);
    }

    public List<MediaItemTypeModel> getMediaItemTypes(int page, int perPage){
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);
        Page<MediaItemTypeModel> mediaItemTypePage = mediaItemTypeRepository.findAll(pageRequest);

        return mediaItemTypePage.getContent();
    }


    public void createMediaItemType(MediaItemTypeModel mediaItemTypeModel) {
        mediaItemTypeRepository.save(mediaItemTypeModel);
    }


    public MediaItemTypeModel updateMediaItemType(UUID id, MediaItemTypeRecordDTO mediaItemTypeData) {
        Optional<MediaItemTypeModel> mediaItemTypeModel = this.getMediaItemTypeById(id);

        if (mediaItemTypeModel.isPresent()) {
            MediaItemTypeModel mediaItemType = mediaItemTypeModel.get();

            if (mediaItemTypeData.name() != null) {
                mediaItemType.setName(mediaItemTypeData.name());
            }

            mediaItemTypeRepository.save(mediaItemType);

            return mediaItemType;

        } else {
            return null;
        }
    }

    public void deleteMediaItemType(UUID id){
        Optional<MediaItemTypeModel> mediaItemTypeModel = this.getMediaItemTypeById(id);

        if (mediaItemTypeModel.isPresent()){
            MediaItemTypeModel mediaItemType = mediaItemTypeModel.get();
            mediaItemTypeRepository.delete(mediaItemType);
        }
    }
}
