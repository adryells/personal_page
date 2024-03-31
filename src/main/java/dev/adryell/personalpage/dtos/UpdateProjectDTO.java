package dev.adryell.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record UpdateProjectDTO(
        @NotBlank(message = "Field can't be blank.") String title,
        @NotBlank(message = "Field can't be blank.") String description,
        Boolean active,
        List<Long> tagIds,
        UUID creatorId
) {
}
