package dev.adryell.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TagDTO(
        @NotNull(message = "Field can't be null.")
        @NotBlank(message = "Field can't be blank.")
        @Size(min = 2, message = "At least 2 chars is required")
        String name,
        Long iconId
) {
}

