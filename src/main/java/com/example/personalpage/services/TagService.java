package com.example.personalpage.services;

import com.example.personalpage.dtos.TagRecordDTO;
import com.example.personalpage.models.TagModel;
import com.example.personalpage.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Optional<TagModel> getTagById(UUID id){
        return tagRepository.findById(id);
    }

    public List<TagModel> getTags(int page, int perPage){
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);
        Page<TagModel> tagPage = tagRepository.findAll(pageRequest);

        return tagPage.getContent();
    }


    public void createTag(TagModel tagModel) {
        tagRepository.save(tagModel);
    }


    public TagModel updateTag(UUID id, TagRecordDTO tagData) {
        Optional<TagModel> tagModel = this.getTagById(id);

        if (tagModel.isPresent()) {
            TagModel tag = tagModel.get();

            if (tagData.name() != null) {
                tag.setName(tagData.name());
            }

            tagRepository.save(tag);

            return tag;

        } else {
            return null;
        }
    }

    public void deleteTag(UUID id){
        Optional<TagModel> tagModel = this.getTagById(id);

        if (tagModel.isPresent()){
            TagModel tag = tagModel.get();
            tagRepository.delete(tag);
        }
    }
}
