package com.example.demo.controller.response;

import com.example.demo.repository.dao.enums.TransportationType;

public record TransportationResponse(
        Long id,
        TransportationType type,
        String originLocationCode,
        String destinationLocationCode
) {
}
