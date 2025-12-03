package com.example.demo.controller.dto;

import com.example.demo.repository.dao.enums.TransportationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTransportationDto(

        @NotNull(message = "type field is required.")
        @Size(max = 10, message = "type field should be smaller than 10 characters.")
        TransportationType type,

        @NotBlank(message = "originLocationCode field is required.")
        @Size(max = 10, message = "originLocationCode field should be smaller than 10 characters.")
        String originLocationCode,

        @NotBlank(message = "destinationLocationCode field is required.")
        @Size(max = 10, message = "destinationLocationCode field should be smaller than 10 characters.")
        String destinationLocationCode
) {
}
