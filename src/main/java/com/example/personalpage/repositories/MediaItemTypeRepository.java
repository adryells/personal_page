package com.example.personalpage.repositories;

import com.example.personalpage.models.MediaItemTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaItemTypeRepository extends JpaRepository<MediaItemTypeModel, UUID> {
}
