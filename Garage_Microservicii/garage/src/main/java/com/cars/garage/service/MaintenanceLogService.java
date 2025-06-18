package com.cars.garage.service;

import com.cars.garage.dto.MaintenanceLogDTO;
import com.cars.garage.entity.MaintenanceLog;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cars.garage.repository.MaintenanceLogRepository;

import java.util.List;

@Service
public class MaintenanceLogService {

    private final MaintenanceLogRepository maintenanceLogRepository;
    private final VehicleService vehicleService;
    private static final Logger logger = LoggerFactory.getLogger(MaintenanceLogService.class);

    @Autowired
    public MaintenanceLogService(MaintenanceLogRepository maintenanceLogRepository, VehicleService vehicleService) {
        this.maintenanceLogRepository = maintenanceLogRepository;
        this.vehicleService = vehicleService;
    }

    public MaintenanceLog saveMaintenanceLog(MaintenanceLog maintenanceLog) {
        logger.info("Saving maintenance log for vehicle with id: {}", maintenanceLog.getVehicle().getId());
        MaintenanceLog savedMaintenanceLog = maintenanceLogRepository.save(maintenanceLog);
        logger.info("Maintenance log saved with id: {}", savedMaintenanceLog.getId());
        return savedMaintenanceLog;
    }

    public MaintenanceLog saveMaintenanceLog(MaintenanceLogDTO maintenanceLogDTO) {
        logger.info("Creating maintenance log for vehicle with id: {}", maintenanceLogDTO.getVehicleId());
        Vehicle vehicle = vehicleService.getVehicleById(maintenanceLogDTO.getVehicleId());

        MaintenanceLog maintenanceLog = new MaintenanceLog();
        maintenanceLog.setVehicle(vehicle);
        maintenanceLog.setDate(maintenanceLogDTO.getDate());
        maintenanceLog.setDescription(maintenanceLogDTO.getDescription());
        maintenanceLog.setCost(maintenanceLogDTO.getCost());
        maintenanceLog.setNextScheduledDate(maintenanceLogDTO.getNextScheduledDate());

        MaintenanceLog savedMaintenanceLog = maintenanceLogRepository.save(maintenanceLog);
        logger.info("Maintenance log created and saved with id: {}", savedMaintenanceLog.getId());
        return savedMaintenanceLog;
    }

    public MaintenanceLog getMaintenanceLogById(Integer id) {
        logger.info("Fetching maintenance log with id: {}", id);
        MaintenanceLog maintenanceLog = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceLog not found with id: " + id));
        logger.info("Maintenance log fetched with id: {}", maintenanceLog.getId());
        return maintenanceLog;
    }

    public List<MaintenanceLog> getAllMaintenanceLogs() {
        logger.info("Fetching all maintenance logs");
        List<MaintenanceLog> maintenanceLogs = maintenanceLogRepository.findAll();
        logger.info("Total maintenance logs fetched: {}", maintenanceLogs.size());
        return maintenanceLogs;
    }

    public void deleteMaintenanceLog(Integer id) {
        logger.info("Attempting to delete maintenance log with id: {}", id);
        if (!maintenanceLogRepository.existsById(id)) {
            logger.error("Maintenance log not found with id: {}", id);
            throw new ResourceNotFoundException("MaintenanceLog not found with id: " + id);
        }
        maintenanceLogRepository.deleteById(id);
        logger.info("Maintenance log with id {} deleted successfully", id);
    }

    public MaintenanceLog updateMaintenanceLog(Integer id, MaintenanceLogDTO maintenanceLogDTO) {
        logger.info("Updating maintenance log with id: {}", id);
        MaintenanceLog existingMaintenanceLog = maintenanceLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MaintenanceLog not found with id: " + id));

        Vehicle vehicle = vehicleService.getVehicleById(maintenanceLogDTO.getVehicleId());

        existingMaintenanceLog.setVehicle(vehicle);
        existingMaintenanceLog.setDate(maintenanceLogDTO.getDate());
        existingMaintenanceLog.setDescription(maintenanceLogDTO.getDescription());
        existingMaintenanceLog.setCost(maintenanceLogDTO.getCost());
        existingMaintenanceLog.setNextScheduledDate(maintenanceLogDTO.getNextScheduledDate());

        MaintenanceLog updatedMaintenanceLog = maintenanceLogRepository.save(existingMaintenanceLog);
        logger.info("Maintenance log with id {} updated successfully", updatedMaintenanceLog.getId());
        return updatedMaintenanceLog;
    }
}
