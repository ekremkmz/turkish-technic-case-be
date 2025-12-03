package com.example.demo.core.converter;

import com.example.demo.controller.response.RouteResponse;
import com.example.demo.repository.specification.projection.RouteProjection;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RouteProjectionToRouteResponseConverter implements Converter<RouteProjection, RouteResponse> {
    @Override
    public RouteResponse convert(RouteProjection source) {
        var flight = new RouteResponse.Flight(
                source.flightFrom(),
                source.flightFromCode(),
                source.flightTo(),
                source.flightToCode()
        );
        var preTransportation = !Objects.isNull(source.preFrom()) ?
                new RouteResponse.PreTransportation(source.preFrom(), source.preType().name()) :
                null;
        var postTransportation = !Objects.isNull(source.postTo()) ?
                new RouteResponse.PostTransportation(source.postTo(), source.postType().name()) :
                null;
        return RouteResponse.builder()
                .flight(flight)
                .preTransportation(preTransportation)
                .postTransportation(postTransportation)
                .build();
    }
}
