package com.cars.microservices.dashboard_service.client;

import com.cars.microservices.dashboard_service.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientServiceClient {
    @GetMapping("/clients/by-vehicle/{vehicleId}")
    ResponseEntity<Client> getClientByVehicleId(@PathVariable("vehicleId") Integer vehicleId);
}