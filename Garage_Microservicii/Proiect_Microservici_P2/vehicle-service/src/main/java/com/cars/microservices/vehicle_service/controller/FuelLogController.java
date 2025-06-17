package com.cars.microservices.vehicle_service.controller;

import com.cars.microservices.vehicle_service.DTO.FuelLogDTO;
import com.cars.microservices.vehicle_service.entity.FuelLog;
import com.cars.microservices.vehicle_service.service.FuelLogService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fuel-logs")
public class FuelLogController {

    private static final Logger logger = LoggerFactory.getLogger(FuelLogController.class);
    private final FuelLogService fuelLogService;

    @Autowired
    public FuelLogController(FuelLogService fuelLogService) {
        this.fuelLogService = fuelLogService;
    }

    @PostMapping
    public ResponseEntity<FuelLog> createFuelLog(@Valid @RequestBody FuelLogDTO fuelLogDTO) {
        logger.info("Received request to create new fuel log");
        FuelLog savedLog = fuelLogService.createFuelLog(fuelLogDTO);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FuelLog> updateFuelLog(@PathVariable Integer id, @Valid @RequestBody FuelLogDTO fuelLogDTO) {
        logger.info("Received request to update fuel log with id: {}", id);
        FuelLog updatedLog = fuelLogService.updateFuelLog(id, fuelLogDTO);
        return ResponseEntity.ok(updatedLog);
    }

    @GetMapping
    public List<FuelLog> getAllFuelLogs() {
        logger.info("Received request to fetch all fuel logs");
        return fuelLogService.getAllFuelLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelLog> getFuelLogById(@PathVariable Integer id) {
        logger.info("Received request to fetch fuel log with id: {}", id);
        FuelLog fuelLog = fuelLogService.getFuelLogById(id);
        return ResponseEntity.ok(fuelLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelLog(@PathVariable Integer id) {
        logger.info("Received request to delete fuel log with id: {}", id);
        fuelLogService.deleteFuelLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-vehicle/{vehicleId}")
    public List<FuelLog> getLogsByVehicleId(@PathVariable Integer vehicleId) {
        logger.info("Received request to fetch fuel logs for vehicleId: {}", vehicleId);
        return fuelLogService.getLogsByVehicleId(vehicleId);
    }
}