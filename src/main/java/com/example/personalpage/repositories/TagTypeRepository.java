package com.example.personalpage.repositories;

import com.example.personalpage.models.TagTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagTypeRepository  extends JpaRepository<TagTypeModel, UUID> {
}
