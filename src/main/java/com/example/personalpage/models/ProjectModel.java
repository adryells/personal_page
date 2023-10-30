package com.example.personalpage.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PROJECTS")
public class ProjectModel extends RepresentationModel<ProjectModel> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title, shortDescription, description, codeLink, projectLink;

    private Integer developmentYear;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<TagModel> tags;

    @OneToMany
    @JoinColumn(name="id")
    private List<MediaItemModel> relatedMedia;

    public UUID getId() {
        return id;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public List<MediaItemModel> getRelatedMedia() {
        return relatedMedia;
    }

    public String getCodeLink() {
        return codeLink;
    }

    public String getProjectLink() {
        return projectLink;
    }

    public Integer getDevelopmentYear() {
        return developmentYear;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setCodeLink(String codeLink) {
        this.codeLink = codeLink;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public void setDevelopmentYear(Integer developmentYear) {
        this.developmentYear = developmentYear;
    }

    public void setRelatedMedia(List<MediaItemModel> relatedMedia) {
        this.relatedMedia = relatedMedia;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
