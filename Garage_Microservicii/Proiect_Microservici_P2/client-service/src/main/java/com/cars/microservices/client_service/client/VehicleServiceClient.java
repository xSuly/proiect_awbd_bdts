package com.cars.microservices.client_service.client;

import com.cars.microservices.client_service.DTO.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VEHICLE-SERVICE")
public interface VehicleServiceClient {

    @GetMapping("/vehicles/{vehicleId}")
    VehicleDTO getVehicleById(@PathVariable("vehicleId") Integer vehicleId);
}
