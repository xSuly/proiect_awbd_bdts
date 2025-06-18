package com.cars.garage.service;

import com.cars.garage.entity.Garage;
import com.cars.garage.entity.Vehicle;
import com.cars.garage.exception.GarageFullException;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    private final GarageService garageService;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, GarageService garageService) {
        this.vehicleRepository = vehicleRepository;
        this.garageService = garageService;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        logger.info("Saving vehicle: {} {}", vehicle.getBrand(), vehicle.getModel());
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle saveVehicleInGarage(Vehicle vehicle, Integer garageId) {
        if (garageId == null) {
            return vehicleRepository.save(vehicle);
        }

        Garage garage = garageService.getGarageById(garageId);

        if (garage.getOccupiedSpaces() >= garage.getCapacity()) {
            throw new GarageFullException("Garajul '" + garage.getLocation() + "' este plin.");
        }

        garage.setOccupiedSpaces(garage.getOccupiedSpaces() + 1);

        vehicle.setGarage(garage);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);


        return savedVehicle;
    }

    @Transactional
    public void deleteVehicleById(Integer vehicleId) {
        Vehicle vehicleToDelete = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Nu a fost gasit niciun vehicul cu ID: " + vehicleId));

        Garage garage = vehicleToDelete.getGarage();

        if (garage != null) {
            if (garage.getOccupiedSpaces() > 0) {
                garage.setOccupiedSpaces(garage.getOccupiedSpaces() - 1);
            }

        }

        vehicleRepository.delete(vehicleToDelete);
    }

    public Vehicle getVehicleById(Integer id) {
        logger.info("Fetching vehicle with id: {}", id);
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    public Page<Vehicle> getAllVehicles(int page, int size, String sortField, String sortDirection) {
        logger.info("Fetching vehicles - page {}, size {}, sortField {}, sortDirection {}", page, size, sortField, sortDirection);

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return vehicleRepository.findAll(pageable);
    }

    public Page<Vehicle> getVehiclesPaginated(int page, String sortField, String sortDir) {
        logger.info("Fetching vehicles for web view - page {}, sortField {}, sortDir {}", page, sortField, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(page, 5, sort);
        return vehicleRepository.findAll(pageable);
    }

    public Page<Vehicle> getAllVehicles(Pageable pageable) {
        logger.info("Fetching all vehicles with pageable: {}", pageable);
        return vehicleRepository.findAll(pageable);
    }

    public void deleteVehicle(Integer id) {
        logger.info("Deleting vehicle with ID: {}", id);
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
        logger.info("Deleted vehicle with ID: {}", id);
    }


    public Vehicle updateVehicle(Integer id, Vehicle vehicle) {
        logger.info("Updating vehicle with ID: {}", id);

        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        existingVehicle.setBrand(vehicle.getBrand());
        existingVehicle.setModel(vehicle.getModel());
        existingVehicle.setProductionYear(vehicle.getProductionYear());
        existingVehicle.setType(vehicle.getType());
        existingVehicle.setCapacity(vehicle.getCapacity());
        existingVehicle.setStatus(vehicle.getStatus());

        return vehicleRepository.save(existingVehicle);
    }
}
