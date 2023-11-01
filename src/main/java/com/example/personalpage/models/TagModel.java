package com.example.personalpage.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TAGS")
public class TagModel extends RepresentationModel<TagModel> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name, description;

    @Column(unique=true)
    private String slug;

    private Timestamp created_at, updated_at;

    @OneToOne
    private TagTypeModel tagType;

    @OneToMany
    @JoinColumn(name="id")
    private List<MediaItemModel> relatedMedia;

    public UUID getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TagTypeModel getTagType() {
        return tagType;
    }

    public List<MediaItemModel> getRelatedMedia() {
        return relatedMedia;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRelatedMedia(List<MediaItemModel> relatedMedia) {
        this.relatedMedia = relatedMedia;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setTagType(TagTypeModel tagType) {
        this.tagType = tagType;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
