package com.example.personalpage.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "MEDIA_ITEMS")
public class MediaItemModel extends RepresentationModel<TagTypeModel> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title, url, mimetype, size;

    private Timestamp created_at, updated_at;

    @Column(nullable = true)
    private MediaItemTypeModel mediaType;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MediaItemTypeModel getMediaType() {
        return mediaType;
    }

    public String getMimetype() {
        return mimetype;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setMediaType(MediaItemTypeModel mediaType) {
        this.mediaType = mediaType;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
