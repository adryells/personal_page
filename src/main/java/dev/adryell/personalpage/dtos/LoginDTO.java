package dev.adryell.personalpage.dtos;

//import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank String email,
        @Size(min = 8, message = "Must contain at least 8 chars.") String password
) {
}
