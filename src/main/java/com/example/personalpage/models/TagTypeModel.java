package com.example.personalpage.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "TAG_TYPES")
public class TagTypeModel extends RepresentationModel<TagTypeModel> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name, description;

    @Column(unique=true)
    private String slug;

    private Timestamp created_at, updated_at;
}
