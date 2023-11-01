package com.example.personalpage.repositories;

import com.example.personalpage.models.MediaItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaItemRepository extends JpaRepository<MediaItemModel, UUID> {
}
