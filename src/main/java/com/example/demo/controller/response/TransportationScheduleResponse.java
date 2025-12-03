package com.example.demo.controller.response;

public record TransportationScheduleResponse(
        Long id,
        TransportationResponse transportation,
        Integer day
) {
}
