package com.example.demo;

import com.example.demo.controller.dto.CreateLocationDto;
import com.example.demo.controller.dto.CreateTransportationDto;
import com.example.demo.controller.dto.CreateTransportationScheduleDto;
import com.example.demo.controller.response.LocationResponse;
import com.example.demo.controller.response.RouteResponse;
import com.example.demo.controller.response.TransportationResponse;
import com.example.demo.repository.dao.TransportationSchedule;
import com.example.demo.repository.dao.enums.TransportationType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class CaseApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void contextLoads() throws Exception {
        CreateLocationDto createLocationDtoA = new CreateLocationDto("A", "A", "A", "A");
        CreateLocationDto createLocationDtoB = new CreateLocationDto("B", "B", "B", "B");
        CreateLocationDto createLocationDtoC = new CreateLocationDto("C", "C", "C", "C");
        CreateLocationDto createLocationDtoD = new CreateLocationDto("D", "D", "D", "D");

        var locationA = post("/v1/location", createLocationDtoA, LocationResponse.class);
        var locationB = post("/v1/location", createLocationDtoB, LocationResponse.class);
        var locationC = post("/v1/location", createLocationDtoC, LocationResponse.class);
        var locationD = post("/v1/location", createLocationDtoD, LocationResponse.class);

        insertTransportationsAndSchedule(locationA, locationB, locationC, locationD);

        var params = MultiValueMap.fromSingleValue(Map.of(
                "from", "A",
                "to", "D",
                "day", "1"
        ));
        var routes = get("/v1/route/search", params, new TypeReference<List<RouteResponse>>() {
        });

        assertEquals(2, routes.size());
        routes.forEach(route -> assertRoute(route, params));

        var params2 = MultiValueMap.fromSingleValue(Map.of(
                "from", "A",
                "to", "D",
                "day", "2"
        ));
        var routes2 = get("/v1/route/search", params2, new TypeReference<List<RouteResponse>>() {
        });

        assertEquals(1, routes2.size());
        routes2.forEach(route -> assertRoute(route, params));

    }

    private void insertTransportationsAndSchedule(LocationResponse locationA, LocationResponse locationB, LocationResponse locationC, LocationResponse locationD) throws Exception {
        CreateTransportationDto createTransportationDtoAB = new CreateTransportationDto(
                TransportationType.BUS,
                locationA.code(),
                locationB.code()
        );
        CreateTransportationDto createTransportationDtoBC = new CreateTransportationDto(
                TransportationType.FLIGHT,
                locationB.code(),
                locationC.code()
        );
        CreateTransportationDto createTransportationDtoAC = new CreateTransportationDto(
                TransportationType.FLIGHT,
                locationA.code(),
                locationC.code()
        );
        CreateTransportationDto createTransportationDtoCD = new CreateTransportationDto(
                TransportationType.UBER,
                locationC.code(),
                locationD.code()
        );

        var transportationAB = post("/v1/transportation", createTransportationDtoAB, TransportationResponse.class);
        var transportationBC = post("/v1/transportation", createTransportationDtoBC, TransportationResponse.class);
        var transportationAC = post("/v1/transportation", createTransportationDtoAC, TransportationResponse.class);
        var transportationCD = post("/v1/transportation", createTransportationDtoCD, TransportationResponse.class);

        CreateTransportationScheduleDto createTransportationScheduleDto1 = new CreateTransportationScheduleDto(transportationAB.id(), 1);
        CreateTransportationScheduleDto createTransportationScheduleDto2 = new CreateTransportationScheduleDto(transportationBC.id(), 1);
        CreateTransportationScheduleDto createTransportationScheduleDto3 = new CreateTransportationScheduleDto(transportationAC.id(), 1);
        CreateTransportationScheduleDto createTransportationScheduleDto4 = new CreateTransportationScheduleDto(transportationCD.id(), 1);

        CreateTransportationScheduleDto createTransportationScheduleDto5 = new CreateTransportationScheduleDto(transportationAB.id(), 2);
        CreateTransportationScheduleDto createTransportationScheduleDto6 = new CreateTransportationScheduleDto(transportationBC.id(), 2);
        CreateTransportationScheduleDto createTransportationScheduleDto7 = new CreateTransportationScheduleDto(transportationCD.id(), 2);

        post("/v1/transportation-schedule", createTransportationScheduleDto1, TransportationSchedule.class);
        post("/v1/transportation-schedule", createTransportationScheduleDto2, TransportationSchedule.class);
        post("/v1/transportation-schedule", createTransportationScheduleDto3, TransportationSchedule.class);
        post("/v1/transportation-schedule", createTransportationScheduleDto4, TransportationSchedule.class);

        post("/v1/transportation-schedule", createTransportationScheduleDto5, TransportationSchedule.class);
        post("/v1/transportation-schedule", createTransportationScheduleDto6, TransportationSchedule.class);
        post("/v1/transportation-schedule", createTransportationScheduleDto7, TransportationSchedule.class);
    }

    private void assertRoute(RouteResponse routeResponse, MultiValueMap<String, String> params) {
        if (Objects.isNull(routeResponse.preTransportation()) && !routeResponse.flight().fromCode().equals(params.get("from").get(0))) {
            Assertions.fail("Route is not correct");
        }
        if (Objects.isNull(routeResponse.postTransportation()) && !routeResponse.flight().toCode().equals(params.get("to").get(0))) {
            Assertions.fail("Route is not correct");
        }
    }

    private <R, B> R post(String url, B b, Class<R> clazz) throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(b))
        ).andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    private <R> R get(String url, MultiValueMap<String, String> params, TypeReference<R> type) throws Exception {
        var result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                .params(params)
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();
        return objectMapper.readValue(result.getResponse().getContentAsString(), type);
    }

}
