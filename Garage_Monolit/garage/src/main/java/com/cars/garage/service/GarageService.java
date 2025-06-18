package com.cars.garage.service;

import com.cars.garage.entity.Garage;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.BadRequestException;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cars.garage.repository.GarageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class GarageService {

    private final GarageRepository garageRepository;
    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(GarageService.class);

    @Autowired
    public GarageService(GarageRepository garageRepository, VehicleRepository vehicleRepository) {
        this.garageRepository = garageRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Garage saveGarage(Garage garage) {
        logger.info("Saving garage with capacity: {}", garage.getCapacity());
        for (Vehicle vehicle : garage.getVehicles()) {
            vehicle.setGarage(garage);
        }

        if (garage.getVehicles().size() > garage.getCapacity()) {
            logger.error("The number of vehicles exceeds the garage capacity for garage with id: {}", garage.getId());
            throw new BadRequestException("The number of vehicles exceeds the garage capacity.");
        }

        garage.setOccupiedSpaces(garage.getVehicles().size());
        Garage savedGarage = garageRepository.save(garage);
        logger.info("Garage saved with id: {}", savedGarage.getId());
        return savedGarage;
    }

    public Garage getGarageById(Integer id) {
        logger.info("Fetching garage with id: {}", id);
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + id));
        logger.info("Garage fetched with id: {}", garage.getId());
        return garage;
    }

    public List<Garage> getAllGarages() {
        logger.info("Fetching all garages");
        List<Garage> garages = garageRepository.findAll();
        logger.info("Total garages fetched: {}", garages.size());
        return garages;
    }

    public void deleteGarage(Integer id) {
        logger.info("Attempting to delete garage with id: {}", id);
        Garage existingGarage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + id));

        for (Vehicle vehicle : existingGarage.getVehicles()) {
            vehicle.setGarage(null);
            vehicleRepository.save(vehicle);
        }

        garageRepository.delete(existingGarage);
        logger.info("Garage with id {} deleted successfully", id);
    }

    public Garage updateGarage(Integer id, Garage updatedGarage) {
        logger.info("Updating garage with id: {}", id);
        Garage existingGarage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + id));

        for (Vehicle vehicle : new ArrayList<>(existingGarage.getVehicles())) {
            vehicle.setGarage(null);
        }
        existingGarage.getVehicles().clear();

        for (Vehicle vehicle : updatedGarage.getVehicles()) {
            vehicle.setGarage(existingGarage);
            existingGarage.addVehicle(vehicle);
        }

        if (existingGarage.getVehicles().size() > existingGarage.getCapacity()) {
            logger.error("The number of vehicles exceeds the garage capacity for garage with id: {}", existingGarage.getId());
            throw new BadRequestException("The number of vehicles exceeds the garage capacity.");
        }

        existingGarage.setOccupiedSpaces(existingGarage.getVehicles().size());
        Garage updatedGarageEntity = garageRepository.save(existingGarage);
        logger.info("Garage with id {} updated successfully", updatedGarageEntity.getId());
        return updatedGarageEntity;
    }
}
