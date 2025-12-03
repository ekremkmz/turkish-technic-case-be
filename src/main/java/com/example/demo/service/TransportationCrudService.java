package com.example.demo.service;

import com.example.demo.controller.dto.CreateTransportationDto;
import com.example.demo.controller.dto.UpdateTransportationDto;
import com.example.demo.controller.response.TransportationResponse;
import com.example.demo.core.exception.SameLocationException;
import com.example.demo.repository.LocationRepository;
import com.example.demo.repository.TransportationRepository;
import com.example.demo.repository.dao.Transportation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransportationCrudService extends BaseCrudService<TransportationResponse, Transportation, Long, CreateTransportationDto, UpdateTransportationDto> {

    @Getter
    private final TransportationRepository crudRepository;

    private final LocationRepository locationRepository;

    @Override
    public TransportationResponse create(CreateTransportationDto createDto) {
        if(createDto.originLocationCode().equals(createDto.destinationLocationCode())){
            throw new SameLocationException();
        }
        return super.create(createDto);
    }

    @Override
    protected TransportationResponse convertEntityToResponse(Transportation entity) {
        return new TransportationResponse(
                entity.getId(),
                entity.getType(),
                entity.getOrigin().getCode(),
                entity.getDestination().getCode()
        );
    }

    @Override
    protected Transportation convertCreateDtoToEntity(CreateTransportationDto createDto) {
        return Transportation.builder()
                .type(createDto.type())
                .origin(locationRepository.getReferenceById(createDto.originLocationCode()))
                .destination(locationRepository.getReferenceById(createDto.destinationLocationCode()))
                .build();
    }

    @Override
    protected Long getId(UpdateTransportationDto updateDto) {
        return updateDto.id();
    }

    @Override
    protected Transportation updateEntity(UpdateTransportationDto updateDto, Transportation entity) {
        if (!entity.getOriginLocationCode().equals(updateDto.originLocationCode())) {
            entity.setOrigin(locationRepository.getReferenceById(updateDto.originLocationCode()));
        }
        if (!entity.getDestinationLocationCode().equals(updateDto.destinationLocationCode())) {
            entity.setDestination(locationRepository.getReferenceById(updateDto.destinationLocationCode()));
        }
        if (!entity.getType().equals(updateDto.type())) {
            entity.setType(updateDto.type());
        }
        return entity;
    }
}
