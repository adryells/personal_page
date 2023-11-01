package com.example.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MediaItemTypeRecordDTO(
        @NotBlank(message = "Name must be filled.") String name,
        @Size(min = 3, message = "Description must contain at least 3 chars.") String description
) {
}
