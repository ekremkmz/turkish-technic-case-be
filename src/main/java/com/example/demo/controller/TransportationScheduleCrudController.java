package com.example.demo.controller;

import com.example.demo.controller.dto.CreateTransportationScheduleDto;
import com.example.demo.controller.response.TransportationScheduleResponse;
import com.example.demo.repository.dao.TransportationSchedule;
import com.example.demo.service.TransportationScheduleCrudService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/transportation-schedule")
@RestController
public class TransportationScheduleCrudController extends BaseCrudController<TransportationScheduleResponse, TransportationSchedule, Long, CreateTransportationScheduleDto, Void> {
    @Getter
    private final TransportationScheduleCrudService crudService;

    @Override
    ResponseEntity<TransportationScheduleResponse> update(Void updateDto) {
        throw new UnsupportedOperationException();
    }
}
