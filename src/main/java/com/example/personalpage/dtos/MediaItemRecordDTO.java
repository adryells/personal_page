package com.example.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MediaItemRecordDTO(
        @NotBlank(message = "URL must be filled.") String url,
        @NotNull(message = "Type id must be filled.") Integer mediaTypeId
) {
}
