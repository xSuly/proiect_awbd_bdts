package com.cars.microservices.garage_service.controller;

import com.cars.microservices.garage_service.entity.Garage;
import com.cars.microservices.garage_service.service.GarageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garages")
public class GarageController {
    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping
    public List<Garage> getAllGarages() {
        return garageService.getAllGarages();
    }

    @GetMapping("/{garageId}")
    public ResponseEntity<Garage> getGarageById(@PathVariable("garageId") Integer garageId) {
        return ResponseEntity.ok(garageService.getGarageById(garageId));
    }
}