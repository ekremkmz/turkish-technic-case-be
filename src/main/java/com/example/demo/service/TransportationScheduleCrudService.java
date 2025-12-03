package com.example.demo.service;

import com.example.demo.controller.dto.CreateTransportationScheduleDto;
import com.example.demo.controller.response.TransportationResponse;
import com.example.demo.controller.response.TransportationScheduleResponse;
import com.example.demo.repository.TransportationRepository;
import com.example.demo.repository.TransportationScheduleRepository;
import com.example.demo.repository.dao.TransportationSchedule;
import com.example.demo.repository.dao.enums.Day;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransportationScheduleCrudService extends BaseCrudService<TransportationScheduleResponse, TransportationSchedule, Long, CreateTransportationScheduleDto, Void> {
    private final TransportationRepository transportationRepository;
    @Getter
    private final TransportationScheduleRepository crudRepository;

    @Override
    protected TransportationScheduleResponse convertEntityToResponse(TransportationSchedule entity) {
        return new TransportationScheduleResponse(
                entity.getId(),
                new TransportationResponse(
                        entity.getTransportation().getId(),
                        entity.getTransportation().getType(),
                        entity.getTransportation().getOriginLocationCode(),
                        entity.getTransportation().getDestinationLocationCode()
                ),
                entity.getOperatingDay().ordinal()
        );
    }

    @Override
    protected TransportationSchedule convertCreateDtoToEntity(CreateTransportationScheduleDto createDto) {
        return TransportationSchedule.builder()
                .transportation(transportationRepository.getReferenceById(createDto.transportationId()))
                .operatingDay(Day.values()[createDto.day()])
                .build();
    }

    @Override
    public TransportationScheduleResponse update(Void updateDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Long getId(Void updateDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected TransportationSchedule updateEntity(Void updateDto, TransportationSchedule entity) {
        throw new UnsupportedOperationException();
    }
}
