package com.cars.microservices.repair_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VEHICLE-SERVICE")
public interface VehicleServiceClient {
    @GetMapping("/vehicles/{vehicleId}")
    ResponseEntity<Void> getVehicleById(@PathVariable("vehicleId") Integer vehicleId);
}
