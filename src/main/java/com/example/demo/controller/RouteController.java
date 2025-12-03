package com.example.demo.controller;

import com.example.demo.controller.dto.RouteCriteriaDto;
import com.example.demo.controller.response.RouteResponse;
import com.example.demo.service.RouteFindingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/route")
@RestController
public class RouteController {

    private final RouteFindingService service;

    @GetMapping("/search")
    ResponseEntity<List<RouteResponse>> search(RouteCriteriaDto dto) {
        return ResponseEntity.ok(service.findRoutes(dto));
    }
}
