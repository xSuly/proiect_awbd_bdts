package com.cars.microservices.dashboard_service.client;

import com.cars.microservices.dashboard_service.model.Garage;
import com.cars.microservices.dashboard_service.model.Mechanic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "GARAGE-SERVICE")
public interface GarageServiceClient {
    @GetMapping("/garages/{id}")
    Garage getGarageById(@PathVariable("id") Integer id);

    @GetMapping("/mechanics/by-garage/{garageId}")
    List<Mechanic> getMechanicsByGarageId(@PathVariable("garageId") Integer garageId);
}
