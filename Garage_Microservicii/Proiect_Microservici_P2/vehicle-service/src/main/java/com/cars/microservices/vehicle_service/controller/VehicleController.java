package com.cars.microservices.vehicle_service.controller;

import com.cars.microservices.vehicle_service.DTO.VehicleDetailDTO;
import com.cars.microservices.vehicle_service.entity.Vehicle;
import com.cars.microservices.vehicle_service.service.VehicleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/vehicles", "/vehicle-service/vehicles"})
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);
    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody Vehicle vehicle) {
        logger.info("Received request to create new vehicle");
        Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
        return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Integer id) {
        logger.info("Received request to fetch vehicle with id: {}", id);
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping
    public Page<Vehicle> getAllVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {
        logger.info("Received request to fetch all vehicles");
        return vehicleService.getAllVehicles(page, size, sortField, sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer id, @Valid @RequestBody Vehicle vehicleDetails) {
        logger.info("Received request to update vehicle with id: {}", id);
        Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDetails);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Integer id) {
        logger.info("Received request to delete vehicle with id: {}", id);
        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<VehicleDetailDTO> getVehicleWithDetails(@PathVariable Integer id) {
        logger.info("Received request to fetch vehicle with garage details for id: {}", id);
        VehicleDetailDTO vehicleDetails = vehicleService.getVehicleWithGarageDetails(id);
        return ResponseEntity.ok(vehicleDetails);
    }
}
