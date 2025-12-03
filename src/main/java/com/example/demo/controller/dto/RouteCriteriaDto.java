package com.example.demo.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RouteCriteriaDto(

        @NotBlank(message = "from field is required.")
        @Size(max = 10, message = "from field should be smaller than 10 characters.")
        String from,

        @NotBlank(message = "to field is required.")
        @Size(max = 10, message = "to field should be smaller than 10 characters.")
        String to,

        @NotNull(message = "day field is required.")
        Long day
) {
}
