package dev.adryell.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectDTO(
        @NotBlank(message = "Field can't be blank.") @NotNull(message = "Field can't be null.") String title,
        @NotBlank(message = "Field can't be blank.") @NotNull(message = "Field can't be null.") String description,
        Boolean active,
        List<Long> tagIds
) {
}
