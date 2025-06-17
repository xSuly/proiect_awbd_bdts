package com.cars.microservices.vehicle_service.controller;

import com.cars.microservices.vehicle_service.DTO.MaintenanceLogDTO;
import com.cars.microservices.vehicle_service.entity.MaintenanceLog;
import com.cars.microservices.vehicle_service.service.MaintenanceLogService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenance-logs")
public class MaintenanceLogController {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceLogController.class);
    private final MaintenanceLogService maintenanceLogService;

    @Autowired
    public MaintenanceLogController(MaintenanceLogService maintenanceLogService) {
        this.maintenanceLogService = maintenanceLogService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceLog> createMaintenanceLog(@Valid @RequestBody MaintenanceLogDTO maintenanceLogDTO) {
        logger.info("Received request to create new maintenance log for vehicleId: {}", maintenanceLogDTO.getVehicleId());
        MaintenanceLog savedLog = maintenanceLogService.createMaintenanceLog(maintenanceLogDTO);
        return new ResponseEntity<>(savedLog, HttpStatus.CREATED);
    }

    @GetMapping
    public List<MaintenanceLog> getAllMaintenanceLogs() {
        logger.info("Received request to fetch all maintenance logs");
        return maintenanceLogService.getAllMaintenanceLogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceLog> getMaintenanceLogById(@PathVariable Integer id) {
        logger.info("Received request to fetch maintenance log with id: {}", id);
        MaintenanceLog log = maintenanceLogService.getMaintenanceLogById(id);
        return ResponseEntity.ok(log);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceLog> updateMaintenanceLog(@PathVariable Integer id, @Valid @RequestBody MaintenanceLogDTO maintenanceLogDTO) {
        logger.info("Received request to update maintenance log with id: {}", id);
        MaintenanceLog updatedLog = maintenanceLogService.updateMaintenanceLog(id, maintenanceLogDTO);
        return ResponseEntity.ok(updatedLog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintenanceLog(@PathVariable Integer id) {
        logger.info("Received request to delete maintenance log with id: {}", id);
        maintenanceLogService.deleteMaintenanceLog(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-vehicle/{vehicleId}")
    public List<MaintenanceLog> getLogsByVehicleId(@PathVariable Integer vehicleId) {
        logger.info("Received request to fetch maintenance logs for vehicleId: {}", vehicleId);
        return maintenanceLogService.getLogsByVehicleId(vehicleId);
    }


}
