package com.cars.microservices.garage_service.service;

import com.cars.microservices.garage_service.DTO.MechanicDTO;
import com.cars.microservices.garage_service.entity.Garage;
import com.cars.microservices.garage_service.entity.Mechanic;
import com.cars.microservices.garage_service.exception.ResourceNotFoundException;
import com.cars.microservices.garage_service.repository.MechanicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {
    private final MechanicRepository mechanicRepository;
    private final GarageService garageService;

    public MechanicService(MechanicRepository mechanicRepository, GarageService garageService) {
        this.mechanicRepository = mechanicRepository;
        this.garageService = garageService;
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Mechanic createMechanic(MechanicDTO mechanicDTO) {
        Mechanic mechanic = new Mechanic();
        mechanic.setName(mechanicDTO.getName());
        mechanic.setSpecialization(mechanicDTO.getSpecialization());

        if (mechanicDTO.getGarageId() != null) {
            Garage garage = garageService.getGarageById(mechanicDTO.getGarageId());
            mechanic.setGarage(garage);
        }
        return mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getMechanicsByGarageId(Integer garageId) {
        return mechanicRepository.findByGarageId(garageId);
    }
}