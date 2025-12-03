package com.example.demo.repository;

import com.example.demo.repository.dao.Transportation;
import com.example.demo.repository.specification.projection.RouteProjection;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface RouteRepository {

    List<RouteProjection> findAllRoutes(Specification<Transportation> spec);
}
