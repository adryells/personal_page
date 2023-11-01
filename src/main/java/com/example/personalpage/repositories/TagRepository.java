package com.example.personalpage.repositories;

import com.example.personalpage.models.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<TagModel, UUID> {
}
