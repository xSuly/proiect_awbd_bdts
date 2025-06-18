package com.cars.garage.controller;

import com.cars.garage.dto.MechanicDTO;
import com.cars.garage.entity.Garage;
import com.cars.garage.entity.Mechanic;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.service.GarageService;
import com.cars.garage.service.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;
    private final GarageService garageService;

    @Autowired
    public MechanicController(MechanicService mechanicService, GarageService garageService) {
        this.mechanicService = mechanicService;
        this.garageService = garageService;
    }

    @PostMapping
    public ResponseEntity<Mechanic> createMechanic(@RequestBody MechanicDTO mechanicDTO) {
        try {
            Mechanic mechanic = new Mechanic();
            mechanic.setName(mechanicDTO.getName());
            mechanic.setSpecialization(mechanicDTO.getSpecialization());

            if (mechanicDTO.getGarageId() != null) {
                Garage garage = garageService.getGarageById(mechanicDTO.getGarageId());
                mechanic.setGarage(garage);
            }

            Mechanic saved = mechanicService.saveMechanic(mechanic);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mechanic> getMechanicById(@PathVariable Integer id) {
        try {
            Mechanic mechanic = mechanicService.getMechanicById(id);
            return ResponseEntity.ok(mechanic);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Mechanic>> getAllMechanics() {
        return ResponseEntity.ok(mechanicService.getAllMechanics());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mechanic> updateMechanic(@PathVariable Integer id, @RequestBody MechanicDTO mechanicDTO) {
        try {
            Mechanic updated = new Mechanic();
            updated.setName(mechanicDTO.getName());
            updated.setSpecialization(mechanicDTO.getSpecialization());

            if (mechanicDTO.getGarageId() != null) {
                Garage garage = garageService.getGarageById(mechanicDTO.getGarageId());
                updated.setGarage(garage);
            }

            Mechanic saved = mechanicService.updateMechanic(id, updated);
            return ResponseEntity.ok(saved);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMechanic(@PathVariable Integer id) {
        try {
            mechanicService.deleteMechanic(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
