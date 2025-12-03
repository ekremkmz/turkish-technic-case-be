package com.example.demo.controller.dto;

import jakarta.validation.constraints.NotNull;

public record CreateTransportationScheduleDto(
        @NotNull Long transportationId,
        @NotNull Integer day
) {
}
