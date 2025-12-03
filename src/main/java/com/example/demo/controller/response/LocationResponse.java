package com.example.demo.controller.response;

public record LocationResponse(
        String code,
        String name,
        String country,
        String city
) {
}
