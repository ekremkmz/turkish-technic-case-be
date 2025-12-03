package com.example.demo.controller;

import com.example.demo.controller.dto.CreateTransportationDto;
import com.example.demo.controller.dto.UpdateTransportationDto;
import com.example.demo.controller.response.TransportationResponse;
import com.example.demo.repository.dao.Transportation;
import com.example.demo.service.TransportationCrudService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/transportation")
@RestController
public class TransportationCrudController extends BaseCrudController<TransportationResponse, Transportation, Long, CreateTransportationDto, UpdateTransportationDto> {
    @Getter
    private final TransportationCrudService crudService;
}
