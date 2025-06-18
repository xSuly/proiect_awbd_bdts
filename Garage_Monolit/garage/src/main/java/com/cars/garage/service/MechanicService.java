package com.cars.garage.service;

import com.cars.garage.entity.Mechanic;
import com.cars.garage.entity.Garage;
import com.cars.garage.exception.ResourceNotFoundException;
import com.cars.garage.repository.MechanicRepository;
import com.cars.garage.repository.GarageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;
    private final GarageRepository garageRepository;
    private static final Logger logger = LoggerFactory.getLogger(MechanicService.class);

    @Autowired
    public MechanicService(MechanicRepository mechanicRepository, GarageRepository garageRepository) {
        this.mechanicRepository = mechanicRepository;
        this.garageRepository = garageRepository;
    }

    public Mechanic saveMechanic(Mechanic mechanic) {
        logger.info("Saving mechanic with name: {}", mechanic.getName());
        if (mechanic.getGarage() != null) {
            Integer garageId = mechanic.getGarage().getId();
            logger.info("Assigning mechanic to garage with id: {}", garageId);
            Garage garage = garageRepository.findById(garageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + garageId));
            mechanic.setGarage(garage);
        }
        Mechanic savedMechanic = mechanicRepository.save(mechanic);
        logger.info("Mechanic saved with id: {}", savedMechanic.getId());
        return savedMechanic;
    }

    public Mechanic getMechanicById(Integer id) {
        logger.info("Fetching mechanic with id: {}", id);
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));
        logger.info("Mechanic found with id: {}", mechanic.getId());
        return mechanic;
    }

    public List<Mechanic> getAllMechanics() {
        logger.info("Fetching all mechanics");
        List<Mechanic> mechanics = mechanicRepository.findAll();
        logger.info("Total mechanics fetched: {}", mechanics.size());
        return mechanics;
    }

    public void deleteMechanic(Integer id) {
        logger.info("Attempting to delete mechanic with id: {}", id);
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));
        mechanicRepository.delete(mechanic);
        logger.info("Mechanic with id {} deleted successfully", id);
    }

    public Mechanic updateMechanic(Integer id, Mechanic updatedMechanic) {
        logger.info("Updating mechanic with id: {}", id);
        Mechanic existingMechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));

        existingMechanic.setName(updatedMechanic.getName());
        existingMechanic.setSpecialization(updatedMechanic.getSpecialization());

        if (updatedMechanic.getGarage() != null) {
            Integer garageId = updatedMechanic.getGarage().getId();
            logger.info("Assigning mechanic to new garage with id: {}", garageId);
            Garage garage = garageRepository.findById(garageId)
                    .orElseThrow(() -> new ResourceNotFoundException("Garage not found with id: " + garageId));
            existingMechanic.setGarage(garage);
        } else {
            existingMechanic.setGarage(null);
        }

        Mechanic updatedMechanicEntity = mechanicRepository.save(existingMechanic);
        logger.info("Mechanic with id {} updated successfully", updatedMechanicEntity.getId());
        return updatedMechanicEntity;
    }
}
