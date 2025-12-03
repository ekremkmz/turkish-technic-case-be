package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateLocationDto(

        @NotBlank(message = "code field is required.")
        @Size(max = 10, message = "code field should be smaller than 10 characters.")
        String code,

        @NotBlank(message = "name field is required.")
        @Size(max = 50, message = "name field should be smaller than 50 characters.")
        String name,

        @NotBlank(message = "country field is required.")
        @Size(max = 50, message = "country field should be smaller than 50 characters.")
        String country,

        @NotBlank(message = "city field is required.")
        @Size(max = 50, message = "city field should be smaller than 50 characters.")
        String city
) {
}
