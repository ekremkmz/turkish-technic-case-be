package com.example.demo.controller.dto;

import com.example.demo.repository.dao.enums.TransportationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTransportationDto(
        @NotNull TransportationType type,
        @NotBlank String originLocationCode,
        @NotBlank String destinationLocationCode
) {
}
