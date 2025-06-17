package com.cars.microservices.vehicle_service.service;

import com.cars.microservices.vehicle_service.DTO.MaintenanceLogDTO;
import com.cars.microservices.vehicle_service.entity.MaintenanceLog;
import com.cars.microservices.vehicle_service.entity.Vehicle;
import com.cars.microservices.vehicle_service.exception.ResourceNotFoundException;
import com.cars.microservices.vehicle_service.repository.MaintenanceLogRepository;
import com.cars.microservices.vehicle_service.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceLogService.class);

    @Autowired
    public MaintenanceLogService(MaintenanceLogRepository maintenanceLogRepository, VehicleRepository vehicleRepository) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public MaintenanceLog createMaintenanceLog(MaintenanceLogDTO maintenanceLogDTO) {
        logger.info("Attempting to create maintenance log for vehicle with id: {}", maintenanceLogDTO.getVehicleId());
        Vehicle vehicle = vehicleRepository.findById(maintenanceLogDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create maintenance log: Vehicle not found with id: " + maintenanceLogDTO.getVehicleId()));

        MaintenanceLog maintenanceLog = new MaintenanceLog();
        maintenanceLog.setVehicle(vehicle);
        maintenanceLog.setDate(maintenanceLogDTO.getDate());
        maintenanceLog.setDescription(maintenanceLogDTO.getDescription());
        maintenanceLog.setCost(maintenanceLogDTO.getCost());
        maintenanceLog.setNextScheduledDate(maintenanceLogDTO.getNextScheduledDate());

        MaintenanceLog savedLog = maintenanceLogRepository.save(maintenanceLog);
        logger.info("Maintenance log created and saved with id: {}", savedLog.getId());
        return savedLog;
    }

    public MaintenanceLog getMaintenanceLogById(Integer id) {
        logger.info("Fetching maintenance log with id: {}", id);
        return maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceLog not found with id: " + id));
    }


    public List<MaintenanceLog> getAllMaintenanceLogs() {
        logger.info("Fetching all maintenance logs");
        List<MaintenanceLog> logs = maintenanceLogRepository.findAll();
        logger.info("Total maintenance logs fetched: {}", logs.size());
        return logs;
    }

    public void deleteMaintenanceLog(Integer id) {
        logger.info("Attempting to delete maintenance log with id: {}", id);
        if (!maintenanceLogRepository.existsById(id)) {
            logger.error("Maintenance log with id {} not found for deletion", id);
            throw new ResourceNotFoundException("MaintenanceLog not found with id: " + id);
        }
        maintenanceLogRepository.deleteById(id);
        logger.info("Maintenance log with id {} deleted successfully", id);
    }

    public MaintenanceLog updateMaintenanceLog(Integer id, MaintenanceLogDTO maintenanceLogDTO) {
        logger.info("Updating maintenance log with id: {}", id);
        MaintenanceLog existingLog = getMaintenanceLogById(id);

        Vehicle vehicle = vehicleRepository.findById(maintenanceLogDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update maintenance log: Vehicle not found with id: " + maintenanceLogDTO.getVehicleId()));

        existingLog.setVehicle(vehicle);
        existingLog.setDate(maintenanceLogDTO.getDate());
        existingLog.setDescription(maintenanceLogDTO.getDescription());
        existingLog.setCost(maintenanceLogDTO.getCost());
        existingLog.setNextScheduledDate(maintenanceLogDTO.getNextScheduledDate());

        MaintenanceLog updatedLog = maintenanceLogRepository.save(existingLog);
        logger.info("Maintenance log with id {} updated successfully", updatedLog.getId());
        return updatedLog;
    }

    public List<MaintenanceLog> getLogsByVehicleId(Integer vehicleId) {
        logger.info("Fetching all maintenance logs for vehicleId: {}", vehicleId);
        return maintenanceLogRepository.findByVehicleId(vehicleId);
    }
}
