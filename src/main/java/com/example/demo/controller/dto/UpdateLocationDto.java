package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateLocationDto(
        @NotBlank String code,
        @NotBlank String name,
        @NotBlank String country,
        @NotBlank String city
) {
}
