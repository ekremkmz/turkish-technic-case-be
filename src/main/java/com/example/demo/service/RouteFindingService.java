package com.example.demo.service;

import com.example.demo.controller.dto.RouteCriteriaDto;
import com.example.demo.controller.response.RouteResponse;
import com.example.demo.repository.RouteRepository;
import com.example.demo.repository.dao.Transportation;
import com.example.demo.repository.specification.RouteSpecification;
import com.example.demo.repository.specification.projection.RouteProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RouteFindingService {

    private final RouteRepository routeRepository;
    private final ConversionService conversionService;

    public List<RouteResponse> findRoutes(RouteCriteriaDto dto) {
        Specification<Transportation> spec = RouteSpecification.buildForRoutes(dto);
        List<RouteProjection> routes = routeRepository.findAllRoutes(spec);
        return routes.stream()
                .map(route -> conversionService.convert(route, RouteResponse.class))
                .toList();
    }

}
