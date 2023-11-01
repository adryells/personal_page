package com.example.personalpage.services;

import com.example.personalpage.dtos.TagTypeRecordDTO;
import com.example.personalpage.models.TagTypeModel;
import com.example.personalpage.repositories.TagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagTypeService {
    @Autowired
    TagTypeRepository tagTypeRepository;

    public TagTypeModel getTagTypeById(UUID id) {
        Optional<TagTypeModel> optional = tagTypeRepository.findById(id);
        return optional.orElse(null);
    }


    public List<TagTypeModel> getTagTypes(int page, int perPage){
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);
        Page<TagTypeModel> tagTypePage = tagTypeRepository.findAll(pageRequest);

        return tagTypePage.getContent();
    }


    public void createTagType(TagTypeModel tagTypeModel) {
        tagTypeRepository.save(tagTypeModel);
    }


    public TagTypeModel updateTagType(UUID id, TagTypeRecordDTO tagTypeData) {
        Optional<TagTypeModel> tagTypeModel = Optional.ofNullable(this.getTagTypeById(id));

        if (tagTypeModel.isPresent()) {
            TagTypeModel tagType = tagTypeModel.get();

            if (tagTypeData.name() != null) {
                tagType.setName(tagTypeData.name());
            }

            tagTypeRepository.save(tagType);

            return tagType;

        } else {
            return null;
        }
    }

    public boolean deleteTagType(UUID id) {
        Optional<TagTypeModel> tagTypeModel = Optional.ofNullable(this.getTagTypeById(id));

        if (tagTypeModel.isPresent()) {
            TagTypeModel tagType = tagTypeModel.get();
            tagTypeRepository.delete(tagType);
            return true;
        } else {
            return false;
        }
    }

}
