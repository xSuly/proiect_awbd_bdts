package com.cars.microservices.garage_service.controller;

import com.cars.microservices.garage_service.DTO.MechanicDTO;
import com.cars.microservices.garage_service.entity.Mechanic;
import com.cars.microservices.garage_service.service.MechanicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {
    private final MechanicService mechanicService;

    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @GetMapping
    public List<Mechanic> getAllMechanics() {
        return mechanicService.getAllMechanics();
    }

    @PostMapping
    public ResponseEntity<Mechanic> createMechanic(@RequestBody MechanicDTO mechanicDTO) {
        Mechanic savedMechanic = mechanicService.createMechanic(mechanicDTO);
        return new ResponseEntity<>(savedMechanic, HttpStatus.CREATED);
    }

    @GetMapping("/by-garage/{garageId}")
    public List<Mechanic> getMechanicsByGarageId(@PathVariable Integer garageId) {
        return mechanicService.getMechanicsByGarageId(garageId);
    }
}