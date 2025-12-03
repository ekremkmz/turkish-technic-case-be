package com.example.demo.controller.dto;


import jakarta.validation.constraints.NotNull;

public record RouteCriteriaDto(
        @NotNull String from,
        @NotNull String to,
        @NotNull Long day
) {
}
