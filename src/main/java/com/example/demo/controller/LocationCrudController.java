package com.example.demo.controller;

import com.example.demo.controller.dto.CreateLocationDto;
import com.example.demo.controller.dto.UpdateLocationDto;
import com.example.demo.controller.response.LocationResponse;
import com.example.demo.repository.dao.Location;
import com.example.demo.service.LocationCrudService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/location")
@RestController
public class LocationCrudController extends BaseCrudController<LocationResponse, Location, String, CreateLocationDto, UpdateLocationDto> {

    @Getter
    private final LocationCrudService crudService;
}
