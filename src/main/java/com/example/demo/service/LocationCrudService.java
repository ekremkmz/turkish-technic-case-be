package com.example.demo.service;

import com.example.demo.controller.dto.CreateLocationDto;
import com.example.demo.controller.dto.UpdateLocationDto;
import com.example.demo.controller.response.LocationResponse;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.dao.Location;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LocationCrudService extends BaseCrudService<LocationResponse, Location, String, CreateLocationDto, UpdateLocationDto> {

    @Getter
    private final LocationRepository crudRepository;

    @Override
    protected LocationResponse convertEntityToResponse(Location entity) {
        return new LocationResponse(
                entity.getCode(),
                entity.getName(),
                entity.getCountry(),
                entity.getCity()
        );
    }

    @Override
    protected Location convertCreateDtoToEntity(CreateLocationDto createDto) {
        return Location.builder()
                .code(createDto.code())
                .name(createDto.name())
                .country(createDto.country())
                .city(createDto.city())
                .build();
    }

    @Override
    protected String getId(UpdateLocationDto updateDto) {
        return updateDto.code();
    }

    @Override
    protected Location updateEntity(UpdateLocationDto updateDto, Location entity) {
        if (!entity.getName().equals(updateDto.name())) {
            entity.setName(updateDto.name());
        }
        if (!entity.getCountry().equals(updateDto.country())) {
            entity.setCountry(updateDto.country());
        }
        if (!entity.getCity().equals(updateDto.city())) {
            entity.setCity(updateDto.city());
        }
        return entity;
    }
}
