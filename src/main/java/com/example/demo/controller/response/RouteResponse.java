package com.example.demo.controller.response;

import lombok.Builder;

@Builder
public record RouteResponse(PreTransportation preTransportation, Flight flight, PostTransportation postTransportation) {

    public record PreTransportation(String fromName, String type) {
    }

    public record Flight(String fromName, String fromCode, String toName, String toCode) {
    }

    public record PostTransportation(String toName, String type) {
    }
}
