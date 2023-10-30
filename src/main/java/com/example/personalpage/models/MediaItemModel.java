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

    private MediaItemTypeModel mediaType;
}
