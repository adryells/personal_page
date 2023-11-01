package com.example.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TagRecordDTO(
        @NotBlank @Size(message = "Name can't be blank.") String name,
        String description,
        @NotNull(message = "Tag type id can't be null") Integer tagTypeId,
        Integer mediaItemId
) {
}
