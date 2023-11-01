package com.example.personalpage.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ProjectRecordDTO(
        @NotBlank @Size(min = 1, message = "Title must contain at least 1 char") String title,
        @NotBlank @Size(min = 3, message = "Short Description must contain at least 3 chars") String shortDescription,
        @NotBlank @Size(min = 3, message = "Short Description must contain at least 3 chars") String description,
        @NotBlank String codeLink,
        @NotBlank String projectLink,
//        @NotNull Integer bannerId,
        @NotNull(message = "Development Year cannot be null") Integer developmentYear
        //        @NotEmpty Array tagIds
) { }
