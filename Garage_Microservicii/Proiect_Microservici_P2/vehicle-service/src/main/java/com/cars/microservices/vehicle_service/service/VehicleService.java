package com.cars.microservices.vehicle_service.service;

import com.cars.microservices.vehicle_service.DTO.GarageDTO;
import com.cars.microservices.vehicle_service.DTO.VehicleDetailDTO;
import com.cars.microservices.vehicle_service.client.GarageServiceClient;
import com.cars.microservices.vehicle_service.entity.Vehicle;
import com.cars.microservices.vehicle_service.exception.ResourceNotFoundException;
import com.cars.microservices.vehicle_service.repository.VehicleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private static final Logger logger = LoggerFactory.getLogger(VehicleService.class);
    private final GarageServiceClient garageServiceClient;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, GarageServiceClient garageServiceClient) {
        this.vehicleRepository = vehicleRepository;
        this.garageServiceClient = garageServiceClient;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        logger.info("Saving vehicle: {} {}", vehicle.getBrand(), vehicle.getModel());
        return vehicleRepository.save(vehicle);
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

    public void deleteVehicle(Integer id) {
        logger.info("Deleting vehicle with ID: {}", id);
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
        logger.info("Deleted vehicle with ID: {}", id);
    }

    public Vehicle updateVehicle(Integer id, Vehicle vehicleDetails) {
        logger.info("Updating vehicle with ID: {}", id);
        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setBrand(vehicleDetails.getBrand());
        existingVehicle.setModel(vehicleDetails.getModel());
        existingVehicle.setProductionYear(vehicleDetails.getProductionYear());
        existingVehicle.setType(vehicleDetails.getType());
        existingVehicle.setCapacity(vehicleDetails.getCapacity());
        existingVehicle.setStatus(vehicleDetails.getStatus());
        existingVehicle.setGarageId(vehicleDetails.getGarageId());

        return vehicleRepository.save(existingVehicle);
    }

    public VehicleDetailDTO getVehicleWithGarageDetails(Integer vehicleId) {
        logger.info("Fetching vehicle with garage details for id: {}", vehicleId);
        Vehicle vehicle = getVehicleById(vehicleId);
        logger.info("Found vehicle with brand {} and garageId: {}", vehicle.getBrand(), vehicle.getGarageId());

        GarageDTO garage = null;
        if (vehicle.getGarageId() != null) {
            logger.info("Calling GarageServiceClient to get details for garageId: {}", vehicle.getGarageId());
            garage = garageServiceClient.getGarageById(vehicle.getGarageId());
            logger.info("Successfully received garage details: {}", garage.location());
        }

        return new VehicleDetailDTO(vehicle, garage);
    }
}
