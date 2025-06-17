package com.cars.microservices.vehicle_service.client;

import com.cars.microservices.vehicle_service.DTO.GarageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "GARAGE-SERVICE")
public interface GarageServiceClient {

    @GetMapping("/garages/{garageId}")
    GarageDTO getGarageById(@PathVariable("garageId") Integer garageId);
}