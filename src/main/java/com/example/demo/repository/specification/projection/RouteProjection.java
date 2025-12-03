package com.example.demo.repository.specification.projection;

import com.example.demo.repository.dao.enums.TransportationType;

public record RouteProjection(
        String flightFrom,
        String flightFromCode,
        String flightTo,
        String flightToCode,
        String preFrom,
        TransportationType preType,
        String postTo,
        TransportationType postType
        ) {
}
