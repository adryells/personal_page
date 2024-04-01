package dev.adryell.personalpage.dtos;

import jakarta.validation.constraints.Size;

public record UpdateTagDTO(
        @Size(min = 2, message = "At least 2 chars is required.") String name,
        Boolean active
) {
}
