package com.cars.microservices.vehicle_service.service;

import com.cars.microservices.vehicle_service.DTO.FuelLogDTO;
import com.cars.microservices.vehicle_service.entity.FuelLog;
import com.cars.microservices.vehicle_service.entity.Vehicle;
import com.cars.microservices.vehicle_service.exception.ResourceNotFoundException;
import com.cars.microservices.vehicle_service.repository.FuelLogRepository;
import com.cars.microservices.vehicle_service.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelLogService {

    private final FuelLogRepository fuelLogRepository;
    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(FuelLogService.class);

    @Autowired
    public FuelLogService(FuelLogRepository fuelLogRepository, VehicleRepository vehicleRepository) {
        this.fuelLogRepository = fuelLogRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public FuelLog createFuelLog(FuelLogDTO fuelLogDTO) {
        logger.info("Attempting to create fuel log for vehicle with id: {}", fuelLogDTO.getVehicleId());
        Vehicle vehicle = vehicleRepository.findById(fuelLogDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot create fuel log: Vehicle not found with id: " + fuelLogDTO.getVehicleId()));

        FuelLog fuelLog = new FuelLog();
        fuelLog.setVehicle(vehicle);
        fuelLog.setDate(fuelLogDTO.getDate());
        fuelLog.setFuelAddedLiters(fuelLogDTO.getFuelAddedLiters());
        fuelLog.setCostPerLiter(fuelLogDTO.getCostPerLiter());
        fuelLog.setTotalCost(fuelLogDTO.getTotalCost());

        FuelLog savedFuelLog = fuelLogRepository.save(fuelLog);
        logger.info("Fuel log saved with id: {}", savedFuelLog.getId());
        return savedFuelLog;
    }

    public FuelLog updateFuelLog(Integer id, FuelLogDTO fuelLogDTO) {
        logger.info("Updating fuel log with id: {}", id);
        FuelLog existingFuelLog = getFuelLogById(id);

        Vehicle vehicle = vehicleRepository.findById(fuelLogDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot update fuel log: Vehicle not found with id: " + fuelLogDTO.getVehicleId()));

        existingFuelLog.setVehicle(vehicle);
        existingFuelLog.setDate(fuelLogDTO.getDate());
        existingFuelLog.setFuelAddedLiters(fuelLogDTO.getFuelAddedLiters());
        existingFuelLog.setCostPerLiter(fuelLogDTO.getCostPerLiter());
        existingFuelLog.setTotalCost(fuelLogDTO.getTotalCost());

        return fuelLogRepository.save(existingFuelLog);
    }

    public FuelLog getFuelLogById(Integer id) {
        logger.info("Fetching fuel log with id: {}", id);
        return fuelLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FuelLog not found with id: " + id));
    }

    public List<FuelLog> getAllFuelLogs() {
        logger.info("Fetching all fuel logs");
        return fuelLogRepository.findAll();
    }

    public void deleteFuelLog(Integer id) {
        logger.info("Attempting to delete fuel log with id: {}", id);
        if (!fuelLogRepository.existsById(id)) {
            throw new ResourceNotFoundException("FuelLog not found with id: " + id);
        }
        fuelLogRepository.deleteById(id);
        logger.info("Fuel log with id {} deleted successfully", id);
    }

    public List<FuelLog> getLogsByVehicleId(Integer vehicleId) {
        logger.info("Fetching all fuel logs for vehicleId: {}", vehicleId);
        return fuelLogRepository.findByVehicleId(vehicleId);
    }
}