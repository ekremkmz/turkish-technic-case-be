package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTransportationScheduleDto(

        @NotNull(message = "transportationId field is required.")
        Long transportationId,

        @NotNull(message = "day field is required.")
        Integer day
) {
}
