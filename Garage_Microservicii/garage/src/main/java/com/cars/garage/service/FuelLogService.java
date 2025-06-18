package com.cars.garage.service;

import com.cars.garage.entity.FuelLog;
import com.cars.garage.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cars.garage.repository.FuelLogRepository;

import java.util.List;

@Service
public class FuelLogService {

    private final FuelLogRepository fuelLogRepository;
    private static final Logger logger = LoggerFactory.getLogger(FuelLogService.class);

    @Autowired
    public FuelLogService(FuelLogRepository fuelLogRepository) {
        this.fuelLogRepository = fuelLogRepository;
    }

    public FuelLog saveFuelLog(FuelLog fuelLog) {
        logger.info("Saving fuel log for vehicle with id: {}", fuelLog.getVehicle().getId());
        FuelLog savedFuelLog = fuelLogRepository.save(fuelLog);
        logger.info("Fuel log saved with id: {}", savedFuelLog.getId());
        return savedFuelLog;
    }

    public FuelLog getFuelLogById(Integer id) {
        logger.info("Fetching fuel log with id: {}", id);
        FuelLog fuelLog = fuelLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FuelLog not found with id: " + id));
        logger.info("Fuel log fetched: {}", fuelLog.getId());
        return fuelLog;
    }

    public List<FuelLog> getAllFuelLogs() {
        logger.info("Fetching all fuel logs");
        List<FuelLog> fuelLogs = fuelLogRepository.findAll();
        logger.info("Total fuel logs fetched: {}", fuelLogs.size());
        return fuelLogs;
    }

    public void deleteFuelLog(Integer id) {
        logger.info("Attempting to delete fuel log with id: {}", id);
        if (!fuelLogRepository.existsById(id)) {
            logger.error("Fuel log with id {} not found for deletion", id);
            throw new ResourceNotFoundException("FuelLog not found with id: " + id);
        }
        fuelLogRepository.deleteById(id);
        logger.info("Fuel log with id {} deleted successfully", id);
    }

    public FuelLog updateFuelLog(Integer id, FuelLog fuelLog) {
        logger.info("Updating fuel log with id: {}", id);
        FuelLog existingFuelLog = fuelLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FuelLog not found with id: " + id));

        existingFuelLog.setVehicle(fuelLog.getVehicle());
        existingFuelLog.setDate(fuelLog.getDate());
        existingFuelLog.setFuelAddedLiters(fuelLog.getFuelAddedLiters());
        existingFuelLog.setCostPerLiter(fuelLog.getCostPerLiter());
        existingFuelLog.setTotalCost(fuelLog.getTotalCost());

        FuelLog updatedFuelLog = fuelLogRepository.save(existingFuelLog);
        logger.info("Fuel log with id {} updated successfully", updatedFuelLog.getId());
        return updatedFuelLog;
    }
}
