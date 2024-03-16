package dev.adryell.personalpage.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Field can't be blank")
        @NotNull(message = "Field can't be null")
        @Email(message = "Invalid e-mail format") String email,
        @NotNull(message = "Field can't be null")
        @Size(min = 8, message = "Must contain at least 8 chars.") String password
) {
}
